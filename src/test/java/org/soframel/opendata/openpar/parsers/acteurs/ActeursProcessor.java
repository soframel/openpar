package org.soframel.opendata.openpar.parsers.acteurs;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soframel.opendata.openpar.OpenparApp;
import org.soframel.opendata.openpar.repository.ActeurRepository;
import org.soframel.opendata.openpar.repository.OrganeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OpenparApp.class)
public class ActeursProcessor {
	private final static Logger log = LoggerFactory
			.getLogger(ActeursProcessor.class);

	@Autowired
	private ActeursParser acteursParser;

	@Autowired
	private OrganesParser organesParser;

	@Autowired
	private ActeurRepository acteurRepository;
	@Autowired
	private OrganeRepository organeRepository;

	@Test
	public void testInsertActeursFull() throws IOException {

		InputStream in = this.getClass().getResourceAsStream(
				"/acteurs-201702-full.xml");

		long time = System.currentTimeMillis();
		acteursParser.parseAndInsert(in);

		long time2 = System.currentTimeMillis();
		log.info("acteurs parsed and inserted in " + (time2 - time) + " ms");

		assertEquals(943, acteurRepository.count());
		in.close();
	}

	@Test
	public void testInsertOrganesFull() throws IOException {
		InputStream in = this.getClass().getResourceAsStream(
				"/acteurs-201702-full.xml");
		long time2 = System.currentTimeMillis();
		organesParser.parseAndInsert(in);
		long time3 = System.currentTimeMillis();

		log.info("organes parsed and inserted in " + (time3 - time2) + " ms");
		in.close();

		assertEquals(1499, organeRepository.count());
	}
}
