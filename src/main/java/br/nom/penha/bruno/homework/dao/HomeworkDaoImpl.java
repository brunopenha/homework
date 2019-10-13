package br.nom.penha.bruno.homework.dao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import br.nom.penha.bruno.homework.entity.Data;
import br.nom.penha.bruno.homework.entity.DataReturn;
import br.nom.penha.bruno.homework.entity.Hosts;
import io.reactivex.Observable;

public class HomeworkDaoImpl implements HomeworkDao {

	Map<Long, DataReturn> dataReturn;
	
	Map<String, Hosts> hostList;

	private static HomeworkDao instance;

	public static HomeworkDao getInstance() {
		return (instance != null) ? instance : new HomeworkDaoImpl();
	}

	private HomeworkDaoImpl() {
		initDB();
	}

	private void initDB() {
		dataReturn = new HashMap<Long, DataReturn>();
//		DataReturn data1 = new DataReturn(new Data(123456789l, new Double(1234.567890)));
//        dataReturn.put(data1.getDataReturn().getTimestamp(),data1);
	}

	@Override
	public Observable<DataReturn> create(DataReturn dto) {
		DataReturn dataToBeReturned = new DataReturn(new Data(dto.getData().getTimestamp(), new Double(dto.getData().getAmount())));
		if(dataReturn.containsKey(dto.getData().getTimestamp())) {
			final DataReturn toAdd = dataReturn.get(dto.getData().getTimestamp());
			
			BigDecimal total = toAdd.getData().getAmountBigDecimal().add(dto.getData().getAmountBigDecimal());
			
			DataReturn added = new DataReturn(new Data(dto.getData().getTimestamp(), total.doubleValue()));
			dataReturn.put(dto.getData().getTimestamp(), added);
		}else {
			dataReturn.put(dataToBeReturned.getData().getTimestamp(), dataToBeReturned);	
		}
		
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
	
	@Override
	public List<DataReturn> readAll(String test) {
        return dataReturn.entrySet().stream().map(entry -> entry.getValue()).collect(Collectors.toList());
    }

}
