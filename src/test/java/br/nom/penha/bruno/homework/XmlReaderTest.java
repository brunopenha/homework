package br.nom.penha.bruno.homework;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.mockito.Mockito;
import org.xml.sax.SAXException;

import br.nom.penha.bruno.homework.entity.Data;
import br.nom.penha.bruno.homework.xml.XmlReader;

public class XmlReaderTest {

	XmlReader xmlReader = XmlReader.getInstance();

    @Test
    public void stringTest(){
        Data o = new Data(123456789l, new Double(1234.567890));
        String xmlString = xmlReader.getXmlOf(o);
        assertEquals(xmlString, "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<data>\n" +
                "    <timestamp>123456789</timestamp>\n" +
                "    <amount>1234.567890</amount>\n" +
                "</data>\n");
    }
    
    @Test
    public void readerTest() throws IOException, ParserConfigurationException, SAXException{
        
        String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
			                "<data>\n" +
			                "    <timestamp>123456789</timestamp>\n" +
			                "    <amount>1234.567890</amount>\n" +
			                "</data>\n";
        
        Data expected = new Data(123456789l, new Double(1234.567890));
        
        
        HttpServletRequest mockRequest = Mockito.mock(HttpServletRequest.class);
        BufferedReader buffReader = new BufferedReader(new StringReader(xmlString));
		Mockito.when(mockRequest.getReader()).thenReturn(buffReader);
		
        Data dto = (Data) XmlReader.getInstance().getDataFromBodyRequest(mockRequest, Data.class);
        
        
        assertEquals(dto.getAmount(),expected.getAmount());
        assertEquals(dto.getTimestamp(),expected.getTimestamp());
    }
    


}

