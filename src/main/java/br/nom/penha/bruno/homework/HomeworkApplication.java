package br.nom.penha.bruno.homework;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

import br.nom.penha.bruno.homework.services.ServiceHandler;
import br.nom.penha.bruno.homework.services.Services;

public class HomeworkApplication extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 161314014280643519L;

	private final static Logger log = Logger.getLogger(HomeworkApplication.class.getName());
	private static final String ARQUIVO_PROPERTIES_APP = "application.properties";

	private int port;
	private ServletHandler servletHandler = new ServletHandler();
	private Map<Class, String> servletsMap = new HashMap<>();
	private List<Services> servicesList = new ArrayList<>();

	
	public HomeworkApplication port(int port) {
		this.port = port;
		return this;
	}

	public HomeworkApplication services(Services services) {
		servicesList.add(services);
		return this;
	}
	
	/**
	 * Here is made the relation between the URL with desire server and the Servlet that implemente this request
	 */
	@Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.startAsync();
        log.info(LocalDateTime.now()+" - " + this.getClass().getSimpleName() + " - start Async context from http request: " + request.getRequestURI());
        try{
            ServiceHandler.getInstance().getEndpointIfMatches(request.getRequestURI()).comunicate(request, response);
        } catch (Exception e){
            log.info(LocalTime.now() + ": Internal Server Error (code 500) " + e.toString());
            request.setAttribute("internal-server-error", e.toString());
            ServiceHandler.getInstance().getEndpointIfMatches("/internal-server-error").comunicate(request, response);
        }
    }

	public void start() throws Exception {

		Server server = new Server(port);

		servletHandler.addServletWithMapping(HomeworkApplication.class, "/*");
		
		if(!servletsMap.isEmpty()){
            for (Map.Entry<Class, String> entry : servletsMap.entrySet()) {
            	
            	servletHandler.addServletWithMapping(entry.getKey(), entry.getValue());
                
                log.info(LocalDateTime.now()+": "+this.getClass().getSimpleName()+" | "+ "Added servlet " + entry.getKey() + "at path " + entry.getValue());
            }
        }
		server.setHandler(servletHandler);
		server.start();
		server.join();
	}
	
	private Properties carregaPropriedades(String nameFile) {

		ClassLoader classLoader = getClass().getClassLoader();
		File arquivoConfiguracao = new File(classLoader.getResource(nameFile).getFile());

		Properties propriedades = null;
		try (FileInputStream arquivo = new FileInputStream(arquivoConfiguracao)) {
			
			propriedades = new Properties();
			propriedades.load(arquivo);
		} catch (FileNotFoundException e) {
			log.severe("Verifique se o arquivo existe dentro do diretorio resources");
		} catch (IOException e) {
			log.severe("Verifique se o arquivo nao contem dados invalidos");
		} 
		return propriedades;
	}
	
	public static void main(String[] args) throws Exception {
		
		HomeworkApplication app = new HomeworkApplication();
		
		
		try {
			app.port = Integer.parseInt(args[0]);
		} catch (ArrayIndexOutOfBoundsException e) {
			app.port = 8080;
		}
		
		
		app.services(new Services()).start();
	}

}
