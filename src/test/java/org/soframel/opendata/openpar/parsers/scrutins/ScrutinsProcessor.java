package org.soframel.opendata.openpar.parsers.scrutins;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soframel.opendata.openpar.OpenparApp;
import org.soframel.opendata.openpar.repository.ScrutinRepository;
import org.soframel.opendata.openpar.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OpenparApp.class)
public class ScrutinsProcessor {
	private final static Logger log = LoggerFactory
			.getLogger(ScrutinsProcessor.class);

	@Autowired
	private ScrutinsParser scrutinsParser;

	@Autowired
	private VotesParser votesParser;

	@Autowired
	private ScrutinRepository scrutinRepository;
	@Autowired
	private VoteRepository voteRepository;

	@Test
	public void testInsertScrutinsFull() throws IOException {
		// FileSystemResourceLoader loader = new FileSystemResourceLoader();
		// Resource scrutinsResource =
		// loader.getResource("../../OpenData/Scrutins_XIV.xml");
		// InputStream in=scrutinsResource.getInputStream();

		InputStream in = this.getClass().getResourceAsStream(
				"/scrutins-20170220-full.xml");

		long time = System.currentTimeMillis();
		scrutinsParser.parseAndInsert(in);

		long time2 = System.currentTimeMillis();
		log.info("scrutins parsed and inserted in " + (time2 - time) + " ms");

		assertEquals(1354, scrutinRepository.count());
		in.close();

		in = this.getClass().getResourceAsStream("/scrutins-20170220-full.xml");
		votesParser.parseAndInsert(in);
		long time3 = System.currentTimeMillis();

		log.info("votes parsed and inserted in " + (time3 - time2) + " ms");
		in.close();

		assertEquals(9405, voteRepository.count());
	}
}
