package org.soframel.opendata.openpar.service;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soframel.opendata.openpar.parsers.scrutins.ScrutinsParser;
import org.soframel.opendata.openpar.parsers.scrutins.VotesParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScrutinsService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private String scrutinsFileUrl = "https://data.assemblee-nationale.fr/static/openData/repository/LOI/scrutins/Scrutins_XIV.xml.zip";

	@Autowired
	private ScrutinsParser scrutinsParser;

	@Autowired
	private VotesParser votesParser;

	public void downloadAndUpdateScrutins() throws ClientProtocolException,
			IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(scrutinsFileUrl);
		CloseableHttpResponse response = httpclient.execute(httpget);
		try {
			log.info("scrutins HTTP status: " + response.getStatusLine());
			int status = response.getStatusLine().getStatusCode();
			if (status >= 200 && status < 300) {
				HttpEntity entity = response.getEntity();
				InputStream in = entity.getContent();
				log.info("Parsing/Inserting scrutins");
				scrutinsParser.parseAndInsert(in);
				// TODO: insert votes with same input stream? duplicate it?
				EntityUtils.consume(entity);
			} else {
				log.error("status was not success: " + status);
				throw new ClientProtocolException(
						"Unexpected response status: " + status);
			}
		} finally {
			log.info("Closing scrutins http connection");
			response.close();
		}
	}
}
