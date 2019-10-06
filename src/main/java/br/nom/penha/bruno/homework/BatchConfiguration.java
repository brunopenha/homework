package br.nom.penha.bruno.homework;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import br.nom.penha.bruno.homework.entity.Data;
import br.nom.penha.bruno.homework.files.FileURLReader;
import br.nom.penha.bruno.homework.mappers.MapeadorDosCamposDoArquivo;
import br.nom.penha.bruno.homework.processors.DataItemProcessor;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

	private static final Logger LOG = LoggerFactory.getLogger(BatchConfiguration.class);

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Autowired
	public DataSource origemDados;
	
	private static final String DB_PROPERTIES = "bancodados.properties";
	private static final String APP_PROPERTIES = "application.properties";

	@Bean
	public DataSource dataSource() {

		Properties dbProperties = loadProperties(DB_PROPERTIES);

		final DriverManagerDataSource database = new DriverManagerDataSource();
		
		if(dbProperties != null){
			database.setDriverClassName(dbProperties.getProperty("banco.driver"));
			database.setUrl(dbProperties.getProperty("banco.url"));
			database.setUsername(dbProperties.getProperty("banco.usuario"));
			database.setPassword(dbProperties.getProperty("banco.senha"));
		}
		
		return database;
	}
	
	private Properties loadProperties(String nameFile) {

		ClassLoader classLoader = getClass().getClassLoader();
		File arquivoConfiguracao = new File(classLoader.getResource(nameFile).getFile());

		Properties propriedades = null;
		try (FileInputStream arquivo = new FileInputStream(arquivoConfiguracao)) {
			
			propriedades = new Properties();
			propriedades.load(arquivo);
		} catch (FileNotFoundException e) {
			LOG.error("Resource file not found inside resource directory");
		} catch (IOException e) {
			LOG.error("Invalid data inside resource directory");
		} 
		return propriedades;
	}

	@Bean
	public FlatFileItemReader<Data> dataXMLReader() {

		Properties propriedadesApp = loadProperties(APP_PROPERTIES);
		FlatFileItemReader<Data> leitor = null;
		
		if(propriedadesApp != null){
			File targetDir = new File(propriedadesApp.getProperty("file"));
	
			try {
				URL url = new URL(propriedadesApp.getProperty("url"));
				FileURLReader.unpackArchive(url, targetDir);
	
			} catch (IOException e) {
				LOG.error("Ocorreu um erro na carga do arquivo: ", e);
			}
	
			try {
				leitor = new FlatFileItemReader<Data>();
				leitor.setResource(new ClassPathResource("datas.xml"));
				leitor.setLinesToSkip(1); // Pulando o cabeçalho
	
				LineMapper<Data> mapeadorTransacao = mapeiaRegistroTransacao();
				leitor.setLineMapper(mapeadorTransacao);
	
			} catch (FlatFileParseException e) {
				LOG.error("Ocorreu um erro: ", e);
			}
		}	
		return leitor;
	}

	private LineMapper<Data> mapeiaRegistroTransacao() {

		DefaultLineMapper<Data> linhaTransacao = new DefaultLineMapper<Data>();
		LineTokenizer registroTransacao = obtemRegistroTranscao();

		linhaTransacao.setLineTokenizer(registroTransacao);
		FieldSetMapper<Data> mapeiaInformacaoTransacao = mapeadorInformacaoTransacao();
		linhaTransacao.setFieldSetMapper(mapeiaInformacaoTransacao);

		return linhaTransacao;

	}

	private FieldSetMapper<Data> mapeadorInformacaoTransacao() {
		return new MapeadorDosCamposDoArquivo();
	}

	private LineTokenizer obtemRegistroTranscao() {

		DelimitedLineTokenizer registro = new DelimitedLineTokenizer(";");
		registro.setNames(new String[] { "id", "dataHora", "remetente", "destinatario", "valor", "tipo" });

		return registro;

	}

	@Bean
	public DataItemProcessor processador() {
		return new DataItemProcessor();
	}

	@Bean
	public JdbcBatchItemWriter<Data> cargaBancoTransacao() {
		JdbcBatchItemWriter<Data> cargaBanco = new JdbcBatchItemWriter<Data>();
		cargaBanco.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Data>());
		cargaBanco.setSql(
				"INSERT INTO data (dataHora, valor) VALUES (:dataHora, :valor)");
		cargaBanco.setDataSource(origemDados);
		return cargaBanco;
	}

	@Bean
	public Step passo1() {

		return stepBuilderFactory
				.get("passo1")
				.<Data, Data>chunk(10)
				.reader(dataXMLReader())
				.processor(processador())
				.writer(cargaBancoTransacao())
				.build();
	}

	@Bean
	public Job tarefaCarregaDadosTransacao() {

		return jobBuilderFactory.get("tarefaCarregaDadosTransacao").incrementer(new RunIdIncrementer())
				.flow(passo1()).end().build();
	}

}
