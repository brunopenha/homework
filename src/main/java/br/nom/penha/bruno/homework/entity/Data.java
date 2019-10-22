package br.nom.penha.bruno.homework.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;

import javax.xml.bind.annotation.XmlElement;

public class Data {

	
	private Long timestamp;
	@XmlElement(name = "amount")
	private BigDecimal amount;
	
	public Data() {
		
	}
	
	public Data(Long datetime, BigDecimal value) {
		super();
		this.timestamp =  datetime;
		this.amount = value.setScale(6, RoundingMode.HALF_UP);
	}
	
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long dataHora) {
		this.timestamp = dataHora;
	}
	
	public BigDecimal getAmount() {
		return amount;
	}
	
//	@JsonbProperty(value = "amountBigDecimal")
//	public BigDecimal getAmountBigDecimal() {
//		return amount;
//	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((timestamp == null) ? 0 : timestamp.hashCode());
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
		Data other = (Data) obj;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (timestamp == null) {
			if (other.timestamp != null)
				return false;
		} else if (!timestamp.equals(other.timestamp))
			return false;
		return true;
	}
	
	
}
