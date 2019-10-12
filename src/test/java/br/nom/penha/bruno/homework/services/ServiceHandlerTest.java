package br.nom.penha.bruno.homework.services;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;

public class ServiceHandlerTest {
	
	ServiceHandler serviceHandler = ServiceHandler.getInstance();
    ServletAction servlet;

    @Before
    public void setUp(){
        servlet = new ServletAction() {
            @Override
            public void comunicate(HttpServletRequest request, HttpServletResponse response) throws IOException {
                //doNothing
            }
        };
        serviceHandler.setService("/test-path-service/{id}", servlet);
    }



	@Test
	public void testGetPathVariables() {
		Map<String, String> pathMaps = serviceHandler.getPathVariables("/test-path-service/23");
        assertEquals(pathMaps.get("id"), "23");
	}

}
