package br.nom.penha.bruno.homework.dao;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.mockito.Mockito;

import br.nom.penha.bruno.homework.entity.Data;
import br.nom.penha.bruno.homework.entity.DataReturn;
import br.nom.penha.bruno.homework.xml.XmlReader;
import io.reactivex.Observable;

public class HomeworkDaoImplTest {

	@Test
	public void testCreate() throws IOException {
		HomeworkDao dao = HomeworkDaoImpl.getInstance();
		
		String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + "<data>\n"
				+ "    <timestamp>123456789</timestamp>\n" + "    <amount>1234.567890</amount>\n" + "</data>\n";

		String xmlString2 = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + "<data>\n"
				+ "    <timestamp>123456789</timestamp>\n" + "    <amount>0.000001</amount>\n" + "</data>\n";
		
		String xmlString3 = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + "<data>\n"
				+ "    <timestamp>123456789</timestamp>\n" + "    <amount>0.000010</amount>\n" + "</data>\n";

		Data expected = new Data(123456789l, new Double(1234.567901));

		HttpServletRequest mockRequest = Mockito.mock(HttpServletRequest.class);
		BufferedReader buffReader = new BufferedReader(new StringReader(xmlString));
		Mockito.when(mockRequest.getReader()).thenReturn(buffReader);
		Data dto = (Data) XmlReader.getInstance().getDataFromBodyRequest(mockRequest, Data.class);
		DataReturn dr1 = new DataReturn(dto);

		HttpServletRequest mockRequest2 = Mockito.mock(HttpServletRequest.class);
		BufferedReader buffReader2 = new BufferedReader(new StringReader(xmlString2));
		Mockito.when(mockRequest2.getReader()).thenReturn(buffReader2);
		Data dto2 = (Data) XmlReader.getInstance().getDataFromBodyRequest(mockRequest2, Data.class);
		DataReturn dr2 = new DataReturn(dto2);
		
		HttpServletRequest mockRequest3 = Mockito.mock(HttpServletRequest.class);
		BufferedReader buffReader3 = new BufferedReader(new StringReader(xmlString3));
		Mockito.when(mockRequest3.getReader()).thenReturn(buffReader3);
		Data dto3 = (Data) XmlReader.getInstance().getDataFromBodyRequest(mockRequest3, Data.class);
		DataReturn dr3 = new DataReturn(dto3);
		
		dao.create(dr1);
		dao.create(dr2);
		dao.create(dr3);
		
		List<DataReturn> total = dao.readAll("test");
		  
		assertEquals( expected.getAmount()   ,total.get(0).getData().getAmount()   );
		assertEquals( expected.getTimestamp(),total.get(0).getData().getTimestamp());
		
	}

}
