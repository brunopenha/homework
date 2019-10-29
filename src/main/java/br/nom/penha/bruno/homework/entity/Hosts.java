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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endpoint == null) ? 0 : endpoint.hashCode());
		result = prime * result + ((hostname == null) ? 0 : hostname.hashCode());
		result = prime * result + port;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Hosts other = (Hosts) obj;
		if (endpoint == null) {
			if (other.endpoint != null)
				return false;
		} else if (!endpoint.equals(other.endpoint))
			return false;
		if (hostname == null) {
			if (other.hostname != null)
				return false;
		} else if (!hostname.equals(other.hostname))
			return false;
		if (port != other.port)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Hosts [hostname=" + hostname + ", port=" + port + ", endpoint=" + endpoint + "]";
	}
	
	

}
