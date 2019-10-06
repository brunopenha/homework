package br.nom.penha.bruno.homework.mappers;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

import br.nom.penha.bruno.homework.entity.Data;

public class MapeadorDosCamposDoArquivo implements FieldSetMapper<Data> {

	@Override
	public Data mapFieldSet(FieldSet doArquivo) throws BindException {
		Data tratado = new Data(doArquivo.readLong("timestamp"), doArquivo.readDouble("amount"));
		return tratado;
	}
	
}

