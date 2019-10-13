package br.nom.penha.bruno.homework.entity;

public class Hosts {

	private String hostname;
	private int port;
	private String endpoint;
	
	public Hosts() {
		
	}
	
	public Hosts(String hostname, int portNumber) {
		super();
		this.hostname = hostname;
		this.port = portNumber;
	}
	
	public Hosts(String hostname, int portNumber, String endpoint) {
		super();
		this.hostname = hostname;
		this.port = portNumber;
		this.endpoint = endpoint;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endPoint) {
		this.endpoint = endPoint;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int portNumber) {
		this.port = portNumber;
	}

}
