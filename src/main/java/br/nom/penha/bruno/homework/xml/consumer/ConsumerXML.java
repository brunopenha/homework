package br.nom.penha.bruno.homework.xml.consumer;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.JAXB;

import br.nom.penha.bruno.homework.dao.HomeworkDao;
import br.nom.penha.bruno.homework.entity.Data;
import br.nom.penha.bruno.homework.entity.LoadStatus;
import br.nom.penha.bruno.homework.services.Services;

public class ConsumerXML {

	private final static Logger log = Logger.getLogger(Services.class.getName());

	private static ConsumerXML instance;

	private final HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();

	private static HomeworkDao dao;

	public static ConsumerXML getInstance(HomeworkDao daoCreated) {
		dao = daoCreated;
		return (instance != null) ? instance : new ConsumerXML();
	}

	public String consumeXml(String url) throws InterruptedException, ExecutionException, TimeoutException {

		HttpRequest request = HttpRequest.newBuilder().GET().uri(URI.create(url))
				.setHeader("User-Agent", "Java 11 HttpClient Bot").build();

		CompletableFuture<HttpResponse<String>> response = httpClient.sendAsync(request,
				HttpResponse.BodyHandlers.ofString());

		return response.thenApply(HttpResponse::body).get(5, TimeUnit.SECONDS);

	}
	
	@Deprecated
	public String consumeXmlOld(String url) {

		StringBuilder sURL = new StringBuilder(100);
		StringBuilder sb1 = new StringBuilder();
		try {

			sURL.append(url);
			InputStream is = new URL(sURL.toString()).openStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			int cp;
			while ((cp = rd.read()) != -1) {
				sb1.append((char) cp);
			}
		} catch (Exception me) {
			log.log(Level.SEVERE, "## consumeXml :" + me.getMessage(), me);
		}

		return sb1.toString();
	}

	public LoadStatus extractXML(final String xmls) {
		LoadStatus status = new LoadStatus();
		LocalDateTime dateTime = LocalDateTime.now();

		dao.createStatus();
		status.setStatus(
				"Started at dd/MM/yyyy -  " + dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));

		Pattern pt = Pattern.compile("<data>(.+?)</data>", Pattern.DOTALL);
		Matcher m = pt.matcher(xmls);

		try {
			for (long i = 0; m.find(); i++) {
				log.log(Level.FINEST, "Matched - [%s]%n", m.group(1)); // outputs [line2]
				final StringBuffer sb = new StringBuffer(86);
				sb.append("<data>").append(m.group(1)).append("</data>");

				Data data = JAXB.unmarshal(new StringReader(sb.toString()), Data.class);
				dao.create(data);

				status.setSize(i);
				status.setStatus("Loading... dd/MM/yyyy - "
						+ dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));

				dao.updateStatus(status);

			}
		} catch (Exception e) {
			status.setError(true);
			status.setStatus(e.getMessage());

			dao.updateStatus(status);

		} finally {
			status.setLoaded(true);
			status.setStatus(
					"Stopped at dd/MM/yyyy -  " + dateTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
			dao.updateStatus(status);
		}

		if (!status.isError()) {
			status.setLoaded(true);
		}
		dao.updateStatus(status);
		return status;
	}

}
