package br.nom.penha.bruno.homework.entity;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

@XmlRootElement(name = "data")
@JsonRootName("data")
@XmlAccessorType(XmlAccessType.NONE)
public class Data {

	
	@XmlElement(name = "timestamp")
	private Long timestamp;
	@XmlElement(name = "amount")
	private BigDecimal amount;
	
	public Data() {
		
	}
	
	public Data(Long datetime, Double value) {
		super();
		this.timestamp =  datetime;
		this.amount = new BigDecimal(value).setScale(6,BigDecimal.ROUND_HALF_UP);
	}
	
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long dataHora) {
		this.timestamp = dataHora;
	}
	
	public String getAmount() {
		return amount.toString();
	}
	
	@JsonIgnore
	@JsonProperty(value = "amountBigDecimal")
	public BigDecimal getAmountBigDecimal() {
		return amount;
	}
	
	
}
