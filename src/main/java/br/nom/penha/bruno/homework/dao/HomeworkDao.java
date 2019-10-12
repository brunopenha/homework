package br.nom.penha.bruno.homework.dao;

import java.util.List;
import java.util.Optional;

import br.nom.penha.bruno.homework.entity.Data;
import br.nom.penha.bruno.homework.entity.DataReturn;
import io.reactivex.Observable;

public interface HomeworkDao {

	Observable<DataReturn> create(Data dto);
	Observable<Optional<DataReturn>> read(Long timestamp);
	Observable<List<DataReturn>> readAll();
}
