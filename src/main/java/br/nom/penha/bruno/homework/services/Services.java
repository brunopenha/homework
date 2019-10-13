package br.nom.penha.bruno.homework.services;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.nom.penha.bruno.homework.dao.HomeworkDao;
import br.nom.penha.bruno.homework.dao.HomeworkDaoImpl;
import br.nom.penha.bruno.homework.entity.DataReturn;
import br.nom.penha.bruno.homework.json.JsonWriter;
import br.nom.penha.bruno.homework.xml.XmlReader;
import io.reactivex.Observable;

public class Services {

	protected ServiceHandler handler = ServiceHandler.getInstance();
	protected JsonWriter jsonWriter = JsonWriter.getInstance();
	protected XmlReader xmlReader = XmlReader.getInstance();
	private final HomeworkDao dao = HomeworkDaoImpl.getInstance();

	ServletAction readAllAction = (HttpServletRequest req, HttpServletResponse res) -> {

		Observable.just(req).flatMap(retorno -> dao.readAll()).subscribe(retorno -> toJsonResponse(req, res, retorno));
	};
	
	ServletAction addAction = (HttpServletRequest req, HttpServletResponse res) -> {

		Observable.just(req) // We want to modified the Observable into another, using the map fuction
		  .map(request -> (DataReturn) getDataFromJsonBodyRequest(req, DataReturn.class)) // we use th
		  .flatMap(dto -> dao.create(dto)) 
		  .subscribe(retorno -> toJsonResponse(req, res,retorno)); 
	};


	public Services() {
		setService("/api/v1/read", readAllAction);
		setService("/api/v1/add", addAction);
	}


	protected synchronized void setService(final String path, final ServletAction action) {
		handler.setService(path, action);
	}

	protected Object getDataFromXmlBodyRequest(final HttpServletRequest request, final Class clazz) throws IOException {
		return xmlReader.getDataFromBodyRequest(request, clazz);
	}

	private void toJsonResponse(HttpServletRequest req, HttpServletResponse res, List<DataReturn> retorno)
			throws IOException {
		handler.toJsonResponse(req, res, retorno);
	}
	
	private void toJsonResponse(HttpServletRequest req, HttpServletResponse res, DataReturn retorno)
			throws IOException {
		handler.toJsonResponse(req, res, retorno);
	}
	
	protected Object getDataFromJsonBodyRequest(final HttpServletRequest request, final Class clazz) throws IOException {
        return jsonWriter.getDataFromBodyRequest(request, clazz);
    }
	
}
