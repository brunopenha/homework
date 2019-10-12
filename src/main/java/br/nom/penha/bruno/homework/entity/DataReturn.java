package br.nom.penha.bruno.homework.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DataReturn {
	
	@JsonProperty("data")
	Data dataReturn;

	
	public DataReturn() {
		
	}
	
	public DataReturn(Data dataObject) {
		super();
		this.dataReturn = dataObject;
	}
	
	public Data getDataReturn() {
		return dataReturn;
	}

	public void setDataReturn(Data dataReturn) {
		this.dataReturn = dataReturn;
	}

}
