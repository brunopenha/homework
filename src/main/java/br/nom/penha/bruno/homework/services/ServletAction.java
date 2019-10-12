package br.nom.penha.bruno.homework.services;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@FunctionalInterface
public interface ServletAction {
    void comunicate(HttpServletRequest request, HttpServletResponse response) throws IOException;
}

