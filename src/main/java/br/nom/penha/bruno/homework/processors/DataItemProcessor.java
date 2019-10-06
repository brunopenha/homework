package br.nom.penha.bruno.homework.processors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

import br.nom.penha.bruno.homework.entity.Data;


public class DataItemProcessor implements ItemProcessor<Data, Data> {

	private static final Logger LOG = LoggerFactory.getLogger(DataItemProcessor.class);
	
	@Override
	public Data process(Data dataParam) throws Exception {
 
		Data dataValidated = null;
		try {
	
			dataValidated = validateTimestamp(dataParam);
						
		} catch (Exception e) {			
			LOG.error("We have an process problem...: " + e.getMessage(), e);
		}
		
		return dataValidated;
	}
	
	/**
	 * If several inputs provide data with the same timestamp - amounts should be merged.
	 * @param dataParam with data to be validated
	 */
	private Data validateTimestamp(Data dataParam) {
		
		/*
		 * Here I should check if this timestamp exist to add to this amount. 
		 */
		
		/*
		 * If not, include new record into database
		 */
		
		return dataParam;
	}


}
