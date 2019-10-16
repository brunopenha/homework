package br.nom.penha.bruno.homework.services;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Interface a Generic HttpServlet service method which is used for any method in a servlet class that provides a service to a client.
 * @author brunopenha
 *
 */
@FunctionalInterface
public interface ServletAction {
    void comunicate(HttpServletRequest request, HttpServletResponse response) throws IOException;
}

