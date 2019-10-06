package br.nom.penha.bruno.homework.processors;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;

import br.nom.penha.bruno.homework.entity.Data;

public class DataItemProcessorTest {

	@Test
	public void testProcess() {
		
		Data d1 = new Data(new Long(123456789),1234.567890);

		//<data> <timestamp>123456789</timeStamp> <amount>1234.567890</amount> </data>

		DataItemProcessor itemProcessado = new DataItemProcessor();
		
		try {
			Data processed = itemProcessado.process(d1);
			
			assertNotNull(processed);
		} catch (Exception e) {
			e.printStackTrace();
			fail("Not yet implemented");
			
		}

	}

}
