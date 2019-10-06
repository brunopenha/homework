package br.nom.penha.bruno.homework;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.nom.penha.bruno.homework.entity.Data;
import br.nom.penha.bruno.homework.processors.DataItemProcessor;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HomeworkApplicationTests {
	
	private Data data;
	
	@Mock
    protected StepExecution stepExecution;
 
    @Mock
    protected JobParameters jobParams;
 
    @InjectMocks
    private DataItemProcessor dataProcessor = new DataItemProcessor();

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {

		MockitoAnnotations.initMocks(this);

		data = createMockData();
		

		Mockito.when(this.stepExecution.getJobParameters()).thenReturn(this.jobParams);
		

	}

	@Test
	public void contextLoads() throws Exception {
		 Data processedData = dataProcessor.process(data);
	     Assert.assertNotNull(processedData);
	     Assert.assertNotNull(processedData.getValor());
	}

	private Data createMockData() {
		//<data> <timestamp>123456789</timeStamp> <amount>1234.567890</amount> </data>
        Data c = new Data(new Long(123456789),1234.567890);
        
        return c;
    }
}
