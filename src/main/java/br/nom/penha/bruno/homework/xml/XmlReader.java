package br.nom.penha.bruno.homework.xml;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.WebTarget;
import javax.xml.bind.JAXB;

import br.nom.penha.bruno.homework.entity.Data;

public class XmlReader {

    private static XmlReader instance = null;
    public static XmlReader getInstance(){
        if(instance == null){
            instance = new XmlReader();
        }
        return instance;
    }

    @SuppressWarnings("unchecked")
	public Object getDataFromBodyRequest(final HttpServletRequest request, final Class clazz) throws IOException {
        StringBuilder sb = new StringBuilder();
        String s;
        while ((s = request.getReader().readLine()) != null) {
            sb.append(s);
        }
        return JAXB.unmarshal(new StringReader(sb.toString()), clazz);
    }
    
    @SuppressWarnings("unchecked")
	public Object getDataFromBodyRequest(final StringReader received, final Class clazz) throws IOException {
        
        return JAXB.unmarshal(received, clazz);
    }

    public String getXmlOf(final Object object){
        StringWriter sw = new StringWriter();
        JAXB.marshal(object, sw);
        return sw.toString();
    }




}