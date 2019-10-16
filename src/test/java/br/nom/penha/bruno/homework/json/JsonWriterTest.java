package br.nom.penha.bruno.homework.json;

import static org.junit.Assert.assertEquals;

import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;

import org.junit.Test;

import br.nom.penha.bruno.homework.entity.Data;
import br.nom.penha.bruno.homework.entity.DataReturn;
import br.nom.penha.bruno.homework.entity.Hosts;

public class JsonWriterTest {

	JsonWriter writer = JsonWriter.getInstance();
	
	@Test
	public void testGetJsonOf()  {
		
		String jsonString = "{\"data\":{\"amount\":\"1234.567890\",\"timestamp\":123456789}}";
	
		
		DataReturn data = new DataReturn(new Data(123456789l, Double.valueOf(1234.567890)));
		assertEquals(jsonString,writer.getJsonOf(data));
	}
	
	@Test
	public void testHostGetJsonOf()  {
		
		String jsonString = "{\"endpoint\":\"/api/v1/readxml\",\"hostname\":\"localhost\",\"port\":8383}";
	
		
		Hosts host = new Hosts("localhost", 8383,"/api/v1/readxml");
		assertEquals(jsonString,writer.getJsonOf(host));
	}
	
	@Test
    public void testSimpleSerialize() {
        Hosts host = new Hosts("localhost", 8383,"/api/v1/readxml");

        JsonbConfig config = new JsonbConfig();
        Jsonb jsonb = JsonbBuilder.create(config);
        final String val = jsonb.toJson(host);
        //{"endpoint":"/api/v1/readxml","hostname":"localhost","port":8383}
        assertEquals("{\"endpoint\":\"/api/v1/readxml\",\"hostname\":\"localhost\",\"port\":8383}", val);
        
		
    }
	

}
