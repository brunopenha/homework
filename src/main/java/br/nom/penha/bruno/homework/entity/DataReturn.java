package br.nom.penha.bruno.homework.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "dataReturn")
public class DataReturn {
	
	@XmlElement(name = "data")
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
