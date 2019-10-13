package br.nom.penha.bruno.homework.entity;

public class LoadStatus {
	
	private boolean loaded;
	private Long size;
	private boolean error;
	private String status;
	
	public LoadStatus() {
		
	}
	
	public LoadStatus(String status) {
		this.status = status;
		size = 0l;
	}
	
	public boolean isLoaded() {
		return loaded;
	}
	public void setLoaded(boolean loaded) {
		this.loaded = loaded;
	}
	public Long getSize() {
		return size;
	}
	public void setSize(Long size) {
		this.size = size;
	}
	public boolean isError() {
		return error;
	}
	public void setError(boolean error) {
		this.error = error;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
