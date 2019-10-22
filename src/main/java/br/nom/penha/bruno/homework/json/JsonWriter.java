package br.nom.penha.bruno.homework.json;

import java.io.IOException;

import javax.json.bind.Jsonb;
import javax.servlet.http.HttpServletRequest;

import org.eclipse.yasson.internal.JsonBindingBuilder;




public class JsonWriter {

    private static JsonWriter instance = null;
    private static Jsonb jsonb = new JsonBindingBuilder().build();

    public static synchronized JsonWriter getInstance(){
        if(instance == null){
            instance = new JsonWriter();
        }
        return instance;
    }

    public String getJsonOf(final Object data) {
    	
    	
        return jsonb.toJson(data) ;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public Object getDataFromBodyRequest(final HttpServletRequest request, final Class clazz) throws IOException {
        StringBuilder sb = new StringBuilder();
        String s;
        while ((s = request.getReader().readLine()) != null) {
            sb.append(s);
        }
        
        
        return jsonb.fromJson(sb.toString(), clazz);
    }

}
