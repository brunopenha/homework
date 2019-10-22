package br.nom.penha.bruno.homework;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
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

	private int port;
	private ServletHandler servletHandler = new ServletHandler();
	@SuppressWarnings("rawtypes")
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
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
	
	
	public static void main(String[] args) throws Exception {
		
		HomeworkApplication app = new HomeworkApplication();
		
		if(args != null && args.length > 0) {
			try {
				
				final int port = Integer.parseInt(args[0]);
				app.port = port;
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Invalid port number --> " + args[0]);
			}
		}
		
		app.services(new Services()).start();
	}

}
