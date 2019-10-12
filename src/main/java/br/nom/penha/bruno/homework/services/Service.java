package br.nom.penha.bruno.homework.services;

public class Service {
	
    private final String path;
    private final ServletAction servletAction ;
    
    public Service() {
    	this.path = "";
		this.servletAction = null;
    }

    public Service(String path, ServletAction action) {
        this.path = path;
        this.servletAction = action;
    }
	

	public synchronized String getPath() {
        return path;
    }

    public synchronized ServletAction getServletAction() {
        return servletAction;
    }
}
