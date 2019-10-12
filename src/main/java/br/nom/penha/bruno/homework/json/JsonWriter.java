package br.nom.penha.bruno.homework.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.nom.penha.bruno.homework.entity.DataReturn;


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


}
