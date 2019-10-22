package br.nom.penha.bruno.homework.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.servlet.AsyncContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.nom.penha.bruno.homework.json.JsonWriter;



public class ServiceHandler {

    private final static Logger log = Logger.getLogger(ServiceHandler.class.getName());

    private static ServiceHandler instance = null;
    
    private final List<Service> serviceList = new ArrayList<>();

    /* Singleton Design pattern implementation */
    public static ServiceHandler getInstance(){
        if(instance == null){
            log.info(LocalTime.now() + ": creation of ServiceHandler");
            instance = new ServiceHandler();
        }
        return instance;
    }
    
    public final synchronized ServiceHandler setService(final String path, final ServletAction action){
        final Service service = new Service(path, action);
        serviceList.add(service);
        log.info(LocalTime.now() + ": added servlet to path " + path);
        return this;
    }

	public void toJsonResponse(HttpServletRequest req, HttpServletResponse res, Object retorno) throws IOException {
		res.setContentType("application/json");
        nioResponse(req, res, JsonWriter.getInstance().getJsonOf(retorno));
	}

	private void nioResponse(HttpServletRequest req, HttpServletResponse res, String jsonOf) throws IOException {
		res.addHeader("Access-Control-Allow-Origin", "*");
// FIXME       final ByteBuffer finalContent = ByteBuffer.wrap(serialize(jsonOf));
		final ByteBuffer finalContent = ByteBuffer.wrap(jsonOf.getBytes());
        final AsyncContext async = req.getAsyncContext();
        final ServletOutputStream out = res.getOutputStream();
        out.setWriteListener(new WriteListener() {

            @Override
            public void onWritePossible() throws IOException {
                while (out.isReady()) {
                    if (!finalContent.hasRemaining()) {
                        res.setStatus(200);
                        async.complete();
                        log.info(LocalDateTime.now()+" - " + this.getClass().getSimpleName() + " - close Async context from http request: " + req.getRequestURI());
                        return;
                    }
                    out.write(finalContent.get());
                }
            }

            @Override
            public void onError(Throwable t) {
                log.info(LocalDateTime.now().toString()+" | "+this.getClass().getSimpleName()+":"+t.toString());
                async.complete();
            }
        });
		
	}

	private byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(obj);
        return out.toByteArray();
    }
    
	public final ServletAction getEndpointIfMatches(final String path){
        List<Service> endpoints = serviceList.stream().filter(end -> urlMatch(path, end.getPath())).limit(1).collect(Collectors.toList());
        return (endpoints.size()==1) ? endpoints.get(0).getServletAction(): getEndpointIfMatches("/not-found") ;
    }

	 private boolean urlMatch(String requestUrl, String endpointUrl){

	        if(!requestUrl.startsWith("/")) {
	        	requestUrl = "/"+requestUrl;
	        }
	        if(!endpointUrl.startsWith("/")) {
	        	endpointUrl = "/"+endpointUrl;
	        }

	        if(requestUrl.endsWith("/")) {
	        	requestUrl = requestUrl.substring(0, requestUrl.length()-1);
	        }
	        if(endpointUrl.endsWith("/")) {
	        	endpointUrl = endpointUrl.substring(0, endpointUrl.length()-1);
	        }

	        String[] splitReq = requestUrl.split("/");
	        String[] splitRes = endpointUrl.split("/");
	        
	        if(splitReq.length != splitRes.length) {
	        	return false;
	        }

	        for(int i=0; i<splitReq.length; i++){
	            String chunckReq = splitReq[i];
	            String chunckRes = splitRes[i];

	            if(!chunckReq.equals(chunckRes)){
	                if(!chunckRes.startsWith("{") && !chunckRes.endsWith("}")) return false;
	            }
	        }
	        return true;
	    }

	    public Map<String, String> getPathVariables(final String path){
	        List<Map<String,String>> pathVariablesListMap = serviceList.stream()
	                    .filter(end -> urlMatch(path, end.getPath()))
	                    .limit(1)
	                    .map(end -> end.getPath())
	                    .map(p -> extractPathVariables(p, path)).collect(Collectors.toList());
	        return (!pathVariablesListMap.isEmpty()) ? pathVariablesListMap.get(0) : new HashMap<String, String>();
	    }

	    private Map<String, String> extractPathVariables(String servicePath, String requestPath){

	        Map<String, String> pathVariables = new HashMap<>();

	        if(!requestPath.startsWith("/")) {
	        	requestPath = "/"+requestPath;
	        }
	        if(!servicePath.startsWith("/")) {
	        	servicePath = "/"+servicePath;
	        }

	        if(requestPath.endsWith("/")) {
	        	requestPath = requestPath.substring(0, requestPath.length()-1);
	        }
	        if(servicePath.endsWith("/")) {
	        	servicePath = servicePath.substring(0, servicePath.length()-1);
	        }

	        String[] splitPath = requestPath.split("/");
	        String[] splitService = servicePath.split("/");

	        for(int i=0; i<splitPath.length; i++){
	            String chunckPath = splitPath[i];
	            String chunckService = splitService[i];

	            if(!chunckPath.equals(chunckService)){
	                if(chunckService.startsWith("{") && chunckService.endsWith("}")) {
	                    String paramName = chunckService.replaceAll("\\{","")
	                           .replaceAll("\\}","")
	                           .replaceAll(" ", "");
	                    pathVariables.put(paramName, chunckPath);
	                }
	            }
	        }
	        return pathVariables;
	    }

}