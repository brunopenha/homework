package br.nom.penha.bruno.homework.entity;

import java.util.Date;

public class ReturnDTO {
    private int status;
    private Object response;
    private Date datetime;

    public ReturnDTO(){}

    public ReturnDTO(int status, Object response) {
        this.status = status;
        this.response = response;
        this.datetime = new Date();
    }

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Object getResponse() {
		return response;
	}

	public void setResponse(Object response) {
		this.response = response;
	}

	public Date getDatetime() {
		return datetime;
	}

	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}
    
    
}
