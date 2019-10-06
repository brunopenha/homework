package br.nom.penha.bruno.homework.entity;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name = "data")
public class Data {

	private String id;
	private LocalDateTime dataHora;
	private BigDecimal valor;
	
	
	public Data(Long datetime, Double value) {
		super();
		this.dataHora =  Instant.ofEpochMilli(datetime).atZone(ZoneId.systemDefault()).toLocalDateTime();
		this.valor = new BigDecimal(value);
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	@XmlElement(name = "timestamp")
	@XmlJavaTypeAdapter(type = LocalDateTime.class, value = br.nom.penha.bruno.homework.adapter.LocalDateTimeAdapter.class)
	public LocalDateTime getDataHora() {
		return dataHora;
	}
	public void setDataHora(LocalDateTime dataHora) {
		this.dataHora = dataHora;
	}
	
	@XmlElement(name = "value")
	public BigDecimal getValor() {
		return valor;
	}
	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}
	
	
	
}
