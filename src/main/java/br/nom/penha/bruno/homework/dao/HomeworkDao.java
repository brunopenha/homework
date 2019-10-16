package br.nom.penha.bruno.homework.dao;

import java.util.List;
import java.util.Optional;

import br.nom.penha.bruno.homework.entity.Data;
import br.nom.penha.bruno.homework.entity.DataReturn;
import br.nom.penha.bruno.homework.entity.Hosts;
import br.nom.penha.bruno.homework.entity.LoadStatus;
import io.reactivex.Observable;

/**
 * Interface to interact with Database
 * 
 * @author bruno.penha
 *
 */
public interface HomeworkDao {

	/**
	 * Insert one XML into database
	 * 
	 * @param dto contains the XML data to be inserted
	 * @return {@link DataReturn} object with the registered information
	 */
	Observable<DataReturn> create(DataReturn dto);

	/**
	 * Insert one XML where does not need to return the data
	 * 
	 * @param dto contains the XML data to be inserted
	 */
	void create(Data dto);

	/**
	 * Read the XML data associated with the Timestamp
	 * 
	 * @param timestamp to identify the XML data
	 * @return the object {@link DataReturn} with the associated @param
	 */
	Observable<Optional<DataReturn>> read(Long timestamp);

	/**
	 * Return all {@link DataReturn} objects stored at database
	 * 
	 * @return Object {@link List} with all {@link DataReturn} data
	 */
	Observable<List<DataReturn>> readAll();

	/**
	 * Method created for Junit to verify if the data with the same timestamp is
	 * associate correctly
	 * 
	 * @param test String just use to test
	 * @return @return Object {@link List} with all {@link DataReturn} data created
	 *         at the test
	 */
	List<DataReturn> readAll(String test);

	/**
	 * Insert one {@link Hosts} into database
	 * 
	 * @param host {@link Hosts} data to be stored
	 * @return object {@link Hosts} stored
	 */
	Observable<Hosts> createHost(Hosts host);

	/**
	 * Return all the {@link Hosts} stored in the database
	 * 
	 * @return object {@link List} with all {@link Hosts} stored
	 */
	Observable<List<Hosts>> readAllHosts();

	/**
	 * Remove the {@link Hosts} associate with the id
	 * 
	 * @param id to identify the {@link Hosts} will be removed
	 * @return object {@link Hosts} deleted or empty value if it not exist
	 */
	Observable<Optional<Hosts>> removeHost(String id);

	/**
	 * Read the {@link Hosts} data associated with the id
	 * 
	 * @param id to identify the {@link Hosts}
	 * @return object {@link Hosts} or empty value if it not exist
	 */
	Observable<Optional<Hosts>> readHost(String id);

	/**
	 * Method (which should be located in another interface) call the remote(s)
	 * host(s) to load all the XML data
	 * 
	 * @return object {@link LoadStatus} with the status of the load 
	 */
	Observable<Optional<LoadStatus>> loadData();

	/**
	 * Create new status into Database
	 */
	void createStatus();

	/**
	 * Update the status according the {@link LoadStatus}
	 * @param status object {@link LoadStatus} with the update data
	 */
	void updateStatus(LoadStatus status);

	/**
	 * Return object {@link LoadStatus} with the actual load status
	 * @return object {@link LoadStatus}
	 */
	Observable<Optional<LoadStatus>> readStatus();
}
