package br.nom.penha.bruno.homework.adapter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime>{
	 
    public LocalDateTime unmarshal(String v) throws Exception {
        return Instant.ofEpochMilli(Long.parseLong(v)).atZone(ZoneId.systemDefault()).toLocalDateTime();
    }
   

	@Override
	public String marshal(LocalDateTime v) throws Exception {
		return v.toString();
	}
 
}
