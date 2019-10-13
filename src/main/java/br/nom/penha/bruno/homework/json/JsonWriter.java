package br.nom.penha.bruno.homework.json;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class JsonWriter {

    private static JsonWriter instance = null;
        

    public static JsonWriter getInstance(){
        if(instance == null){
            instance = new JsonWriter();
        }
        return instance;
    }

    public String getJsonOf(final Object data) throws JsonProcessingException{
    	
    	final ObjectMapper mapper = new ObjectMapper();
    	
        return mapper.writeValueAsString(data);
    }
    
    public Object getDataFromBodyRequest(final HttpServletRequest request, final Class clazz) throws IOException {
        StringBuilder sb = new StringBuilder();
        String s;
        while ((s = request.getReader().readLine()) != null) {
            sb.append(s);
        }
        final ObjectMapper objectMapper = new ObjectMapper();
        
        return objectMapper.readValue(sb.toString(), clazz);
    }

}
