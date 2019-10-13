package br.nom.penha.bruno.homework.entity;

public class DataReturn {
	
	Data data;
	
	public DataReturn() {
		
	}
	
	public DataReturn(Data dataObject) {
		super();
		this.data = dataObject;
	}
	
	public Data getData() {
		return data;
	}

	public void setData(Data dataReturn) {
		this.data = dataReturn;
	}

}
