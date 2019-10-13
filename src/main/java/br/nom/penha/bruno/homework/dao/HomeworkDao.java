package br.nom.penha.bruno.homework.dao;

import java.util.List;
import java.util.Optional;

import br.nom.penha.bruno.homework.entity.DataReturn;
import br.nom.penha.bruno.homework.entity.Hosts;
import io.reactivex.Observable;

public interface HomeworkDao {

	Observable<DataReturn> create(DataReturn dto);
	Observable<Optional<DataReturn>> read(Long timestamp);
	Observable<List<DataReturn>> readAll();
	List<DataReturn> readAll(String test);
	Observable<Hosts> createHost(Hosts host);
	Observable<List<Hosts>> readAllHosts();
	Observable<Optional<Hosts>> removeHost(String id);
	Observable<Optional<Hosts>> readHost(String id);
}
