package org.soframel.opendata.openpar.parsers.scrutins;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soframel.opendata.openpar.OpenparApp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;
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

	@Test
	public void processScrutins() throws IOException {
		FileSystemResourceLoader loader = new FileSystemResourceLoader();
		Resource scrutinsResource = loader
				.getResource("../../OpenData/Scrutins_XIV.xml");
		long time = System.currentTimeMillis();
		log.debug("Scrutins resource found ? " + scrutinsResource.exists());
		scrutinsParser.parseAndInsert(scrutinsResource.getInputStream());

		long time2 = System.currentTimeMillis();
		log.info("scrutins parsed and inserted in " + (time2 - time) + " ms");

		// TODO: add votes
	}
}
