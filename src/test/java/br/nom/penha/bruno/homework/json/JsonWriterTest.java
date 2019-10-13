package br.nom.penha.bruno.homework.json;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.nom.penha.bruno.homework.entity.Data;
import br.nom.penha.bruno.homework.entity.DataReturn;
import br.nom.penha.bruno.homework.entity.Hosts;

public class JsonWriterTest {

	JsonWriter writer = JsonWriter.getInstance();
	
	@Test
	public void testGetJsonOf() throws JsonProcessingException {
		
		String jsonString = "{\"data\":{\"timestamp\":123456789,\"amount\":\"1234.567890\"}}";
	
		
		DataReturn data = new DataReturn(new Data(123456789l, new Double(1234.567890)));
		assertEquals(jsonString,writer.getJsonOf(data));
	}
	
	@Test
	public void testHostGetJsonOf() throws JsonProcessingException {
		
		String jsonString = "{\"hostname\":\"localhost\",\"port\":8383,\"endpoint\":\"/api/v1/readxml\"}";
	
		
		Hosts host = new Hosts("localhost", 8383,"/api/v1/readxml");
		assertEquals(jsonString,writer.getJsonOf(host));
	}
	

}
