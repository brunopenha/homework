package br.nom.penha.bruno.homework.dao;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import br.nom.penha.bruno.homework.entity.Data;
import br.nom.penha.bruno.homework.entity.DataReturn;
import br.nom.penha.bruno.homework.entity.Hosts;
import br.nom.penha.bruno.homework.entity.LoadStatus;
import br.nom.penha.bruno.homework.xml.consumer.ConsumerXML;
import io.reactivex.Observable;

public class HomeworkDaoImpl implements HomeworkDao {

	private final static Logger log = Logger.getLogger(HomeworkDaoImpl.class.getName());

	Map<Long, DataReturn> dataReturn;
	
	Map<String, Hosts> hostList;
	
	LoadStatus loadStatus;

	private static HomeworkDao instance;

	public static HomeworkDao getInstance() {
		return (instance != null) ? instance : new HomeworkDaoImpl();
	}

	private HomeworkDaoImpl() {
		initDB();
	}

	private void initDB() {
		dataReturn = new HashMap<Long, DataReturn>();
		hostList = new HashMap<String, Hosts>();
		loadStatus = new LoadStatus();
	}

	@Override
	public Observable<DataReturn> create(DataReturn dto) {
		DataReturn dataToBeReturned = new DataReturn(new Data(dto.getData().getTimestamp(), Double.valueOf(dto.getData().getAmount())));
		if(dataReturn.containsKey(dto.getData().getTimestamp())) {
			final DataReturn toAdd = dataReturn.get(dto.getData().getTimestamp());
			
			BigDecimal total = new BigDecimal(toAdd.getData().getAmount()).add(new BigDecimal(dto.getData().getAmount()));
			
			DataReturn added = new DataReturn(new Data(dto.getData().getTimestamp(), total.doubleValue()));
			dataReturn.replace(dto.getData().getTimestamp(), added);
		}else {
			dataReturn.put(dataToBeReturned.getData().getTimestamp(), dataToBeReturned);	
		}
		
        return  Observable.fromCallable(() -> dataToBeReturned);
	}
	
	@Override
	public void create(Data dto) {
		DataReturn dataToBeReturned = new DataReturn(new Data(dto.getTimestamp(), Double.valueOf(dto.getAmount())));
		if(dataReturn.containsKey(dto.getTimestamp())) {
			final DataReturn toAdd = dataReturn.get(dto.getTimestamp());
			
			BigDecimal total = new BigDecimal(toAdd.getData().getAmount()).add(new BigDecimal(dto.getAmount()));
			
			DataReturn added = new DataReturn(new Data(dto.getTimestamp(), total.doubleValue()));
			dataReturn.replace(dto.getTimestamp(), added);
		}else {
			dataReturn.put(dataToBeReturned.getData().getTimestamp(), dataToBeReturned);	
		}
		log.log(Level.FINE, "******** XML sLoaded ********");
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
	
	@Override
	public Observable<List<Hosts>> readAllHosts() {
		return Observable.fromCallable(() -> new ArrayList<>(hostList.values()));
	}
	
	@Override
	public Observable<Hosts> createHost(Hosts host) {
		final String key = host.getHostname() + host.getPort();
		if(hostList.containsKey(key)) {
			hostList.replace(key, host);
		}else {
			hostList.put(key, host);	
		}
		
        return  Observable.fromCallable(() -> host);
	}

	@Override
	public Observable<Optional<Hosts>> removeHost(String stringId) {
		
		if(hostList.containsKey(stringId)) {
			hostList.remove(stringId);
		}
		
		return Observable.fromCallable(() -> Optional.ofNullable(hostList.get(stringId)));
        
	}

	@Override
	public Observable<Optional<Hosts>> readHost(String stringId) {
		return Observable.fromCallable(() -> Optional.ofNullable(hostList.get(stringId)));
	}

	@Override
	public Observable<Optional<LoadStatus>> loadData() {
		
		ConsumerXML consumer = ConsumerXML.getInstance(this);
		try {
			for (Hosts host : hostList.values()) {
				//FIXME Normally should be an layer to request external service
				final String xmls = consumer.consumeXml("http://" + host.getHostname() + ":" + host.getPort() + host.getEndpoint());
				loadStatus = consumer.extractXML(xmls);
				
			}
		} catch (Exception e) {

			loadStatus.setError(true);
			loadStatus.setStatus(e.getMessage());
		
		}
		
		return Observable.fromCallable(() -> Optional.ofNullable(loadStatus));
	}

	@Override
	public void createStatus() {
		
		loadStatus = new LoadStatus();
		loadStatus.setSize(0l);
		loadStatus.setError(false);
		loadStatus.setLoaded(false);
		loadStatus.setStatus("Started in dd/MM/yyyy -  " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
		
	}

	@Override
	public void updateStatus(LoadStatus status) {
		if(status != null) {
			loadStatus = status;
		}
		
	}

	@Override
	public Observable<Optional<LoadStatus>> readStatus() {
		return Observable.fromCallable(() -> Optional.ofNullable(loadStatus));
	}


}
