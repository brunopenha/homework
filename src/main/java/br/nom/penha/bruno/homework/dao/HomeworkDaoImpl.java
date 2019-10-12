package br.nom.penha.bruno.homework.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import br.nom.penha.bruno.homework.entity.Data;
import br.nom.penha.bruno.homework.entity.DataReturn;
import io.reactivex.Observable;

public class HomeworkDaoImpl implements HomeworkDao {

	Map<Long, DataReturn> dataReturn;

	private static HomeworkDao instance;

	public static HomeworkDao getInstance() {
		return (instance != null) ? instance : new HomeworkDaoImpl();
	}

	private HomeworkDaoImpl() {
		initDB();
	}

	private void initDB() {
		dataReturn = new HashMap<Long, DataReturn>();
		DataReturn data1 = new DataReturn(new Data(123456789l, new Double(1234.567890)));
        dataReturn.put(data1.getDataReturn().getTimestamp(),data1);
	}

	@Override
	public Observable<DataReturn> create(Data dto) {
		DataReturn dataToBeReturned = new DataReturn(new Data(dto.getTimestamp(), new Double(dto.getAmount())));
		if(dataReturn.containsKey(dto.getTimestamp())) {
			final DataReturn toAdd = dataReturn.get(dto.getTimestamp());
			DataReturn added = new DataReturn(new Data(dto.getTimestamp(), new Double(toAdd.getDataReturn().getAmount()) + new Double(dto.getAmount())));
			dataReturn.put(dto.getTimestamp(), added);
		}
		dataReturn.put(dataToBeReturned.getDataReturn().getTimestamp(), dataToBeReturned);
        return  Observable.fromCallable(() -> dataToBeReturned);
	}

	@Override
	public Observable<Optional<DataReturn>> read(Long timestamp) {
		return Observable.fromCallable(() -> Optional.ofNullable(dataReturn.get(timestamp)));
	}

	@Override
	public Observable<List<DataReturn>> readAll() {
		return Observable.fromCallable(() -> new ArrayList<>(dataReturn.values()));
	}

}
