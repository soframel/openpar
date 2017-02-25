package org.soframel.opendata.openpar.parsers.scrutins;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soframel.opendata.openpar.parsers.OpenDataAbstractParser;
import org.soframel.opendata.openpar.repository.ScrutinRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public class ScrutinsParser extends OpenDataAbstractParser {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ScrutinRepository scrutinRepository;

	@Override
	public void parseAndInsert(InputStream in) {
		try {
			XMLReader xmlReader = this.getSAXXMLReader();
			xmlReader.setContentHandler(new ScrutinsContentHandler(
					scrutinRepository));
			InputSource source = new InputSource(in);
			xmlReader.parse(source);
		} catch (ParserConfigurationException e) {
			log.error("ParserConfigurationException " + e.getMessage(), e);
		} catch (SAXException e) {
			log.error("SAXException " + e.getMessage(), e);
		} catch (IOException e) {
			log.error("IOException " + e.getMessage(), e);
		}
	}

	public ScrutinRepository getScrutinRepository() {
		return scrutinRepository;
	}

	public void setScrutinRepository(ScrutinRepository scrutinRepository) {
		this.scrutinRepository = scrutinRepository;
	}

}
