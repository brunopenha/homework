package br.nom.penha.bruno.homework.services;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.HttpMethod;

import br.nom.penha.bruno.homework.dao.HomeworkDao;
import br.nom.penha.bruno.homework.dao.HomeworkDaoImpl;
import br.nom.penha.bruno.homework.entity.DataReturn;
import br.nom.penha.bruno.homework.entity.Hosts;
import br.nom.penha.bruno.homework.json.JsonWriter;
import br.nom.penha.bruno.homework.xml.XmlReader;
import io.reactivex.Observable;

public class Services {
	
//	private final static Logger log = Logger.getLogger(Services.class.getName());

	protected ServiceHandler handler = ServiceHandler.getInstance();
	protected JsonWriter jsonWriter = JsonWriter.getInstance();
	protected XmlReader xmlReader = XmlReader.getInstance();
	private final HomeworkDao dao = HomeworkDaoImpl.getInstance();

	ServletAction readAllData = (HttpServletRequest req, HttpServletResponse res) -> {

		Observable.just(req).flatMap(retorno -> dao.readAll()).subscribe(retorno -> toJsonResponse(req, res, retorno));
	};

	ServletAction addData = (HttpServletRequest req, HttpServletResponse res) -> {

		Observable.just(req) // We want to modified the Observable into another, using the map fuction
				.map(request -> (DataReturn) getDataFromXmlBodyRequest(req, DataReturn.class)) // we use th
				.flatMap(dto -> dao.create(dto)).subscribe(retorno -> toJsonResponse(req, res, retorno));
	};

	ServletAction addHosts = (HttpServletRequest req, HttpServletResponse res) -> {

		Observable.just(req) // We want to modified the Observable into another, using the map fuction
				.map(request -> (Hosts) getDataFromJsonBodyRequest(req, Hosts.class))
				.flatMap(dto -> dao.createHost(dto))
				.subscribe(retorno -> toJsonResponse(req, res, retorno));

	};

	ServletAction readHosts = (HttpServletRequest req, HttpServletResponse res) -> {

		Observable.just(req)
			.flatMap(retorno -> dao.readAllHosts())
			.subscribe(retorno -> toJsonResponse(req, res, retorno));
	};
	
	ServletAction getXML = (HttpServletRequest req, HttpServletResponse res) -> {

		Observable.just(req)
			.flatMap(retorno -> dao.loadData())
			.subscribe(retorno -> toJsonResponse(req, res, retorno.get()));
	};
	
	

	ServletAction readHostsQuery = (HttpServletRequest req, HttpServletResponse res) -> {
		
		
		switch (req.getMethod()) {
		case HttpMethod.GET:
			Observable.just(req) // We want to modified the Observable into another, using the map fuction
			.map(request -> getPathVariables(req).get("host"))
			.flatMap(id -> dao.readHost(id))
			.subscribe(retorno -> toJsonResponse(req, res, retorno.get()));			
			
			
			break;
		case HttpMethod.DELETE:
			
			Observable.just(req) // We want to modified the Observable into another, using the map fuction
			.map(request -> getPathVariables(req).get("host"))
			.map(id -> dao.removeHost(id))
			.subscribe(retornoDel -> toJsonResponse(req, res, retornoDel));
			
			break;
		}

	};
	
	ServletAction readStatus = (HttpServletRequest req, HttpServletResponse res) -> {

		Observable.just(req).flatMap(retorno -> dao.readStatus()).subscribe(retorno -> toJsonResponse(req, res, retorno.get()));
	};

	public Services() {
		setService("/api/v1/read", readAllData);
		setService("/api/v1/add", addData);
		setService("/api/v1/setup", addHosts);
		setService("/api/v1/hosts/{host}", readHostsQuery);
		setService("/api/v1/hosts", readHosts);
		setService("/api/v1/load", getXML);
		setService("/api/v1/status", readStatus);
	}

	protected synchronized void setService(final String path, final ServletAction action) {
		handler.setService(path, action);
	}

	protected Object getDataFromXmlBodyRequest(final HttpServletRequest request, @SuppressWarnings("rawtypes") final Class clazz) throws IOException {
		return xmlReader.getDataFromBodyRequest(request, clazz);
	}

	private void toJsonResponse(HttpServletRequest req, HttpServletResponse res, Object retorno) throws IOException {
		handler.toJsonResponse(req, res, retorno);
	}

//	private void toJsonResponse(HttpServletRequest req, HttpServletResponse res, List<DataReturn> retorno)
//			throws IOException {
//		handler.toJsonResponse(req, res, retorno);
//	}
//	
//	private void toJsonResponse(HttpServletRequest req, HttpServletResponse res, DataReturn retorno)
//			throws IOException {
//		handler.toJsonResponse(req, res, retorno);
//	}

	@SuppressWarnings("rawtypes")
	protected Object getDataFromJsonBodyRequest(final HttpServletRequest request, final Class clazz)
			throws IOException {
		return jsonWriter.getDataFromBodyRequest(request, clazz);
	}

	protected Map<String, String> getPathVariables(final HttpServletRequest request) {
		return handler.getPathVariables(request.getRequestURI());
	}

}
