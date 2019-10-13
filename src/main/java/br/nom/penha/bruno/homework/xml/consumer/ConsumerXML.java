package br.nom.penha.bruno.homework.xml.consumer;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import br.nom.penha.bruno.homework.entity.Data;
import br.nom.penha.bruno.homework.entity.DataReturn;

public class ConsumerXML {

	public static void main(String[] args) throws JAXBException {

		Map<Long, DataReturn> listaTeste = ConsumerXML.consumeXml("http://localhost:8383/api/v1/readxml");
		System.out.println(listaTeste.size());

	}

	public static Map<Long, DataReturn> consumeXml(String url) throws JAXBException {

		Map<Long, DataReturn> dataReceive = null;
//		Client client = ClientBuilder.newClient();
////		Response response = client.target(url).request().get();
//		
//		WebTarget webTarget = client.target("http://localhost:8383/");
//		WebTarget dataWebTarget  = webTarget.path("rapi/v1/readxml");
//		Data dto = (Data) XmlReader.getInstance().getDataFromBodyRequest(dataWebTarget, Data.class);

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
			System.out.println("## Exception :" + me.getMessage());
		}
		String apiOutput = removeCaracters(sb1.toString(), "[^a-zA-Z0-9.\n </>]").replaceAll("(?m)<xml.*", "");
		apiOutput = removeCaracters(apiOutput, "xml file content : tH");
		apiOutput = removeCaracters(apiOutput, "xml file content : t7\n");
		apiOutput = removeCaracters(apiOutput, "t7\n");
		System.out.println("xml file content : " + apiOutput);
		JAXBContext jaxbContext = JAXBContext.newInstance(DataReturn.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		StringReader reader = new StringReader(apiOutput);

		DataReturn uu = (DataReturn) unmarshaller.unmarshal(reader);

//        for (int i=0; i<uu.getDataReturn().size();i++) {
//        	Data u = uu.getData.get(i);
//            System.out.println("Yes:"+u.getAmount());
//        }
		return dataReceive;
	}

	private static String removeCaracters(String output, String pattern) {
		Pattern pt = Pattern.compile(pattern);
		Matcher match = pt.matcher(output);
		while (match.find()) {
			final String acceptCaracters = match.group();
			output = output.replaceAll("\\" + acceptCaracters, "");
		}

		return output;
	}

}
