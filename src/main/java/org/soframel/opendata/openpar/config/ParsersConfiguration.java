package org.soframel.opendata.openpar.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soframel.opendata.openpar.parsers.acteurs.ActeursParser;
import org.soframel.opendata.openpar.parsers.acteurs.OrganesParser;
import org.soframel.opendata.openpar.parsers.scrutins.ScrutinsParser;
import org.soframel.opendata.openpar.parsers.scrutins.VotesParser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

@Configuration
public class ParsersConfiguration {

	@SuppressWarnings("unused")
	private final Logger log = LoggerFactory
			.getLogger(ParsersConfiguration.class);

	@Bean
	@Description("Scrutins parser")
	public ScrutinsParser createScrutinParser() {
		ScrutinsParser parser = new ScrutinsParser();
		return parser;
	}

	@Bean
	@Description("Votes parser")
	public VotesParser createVotesParser() {
		VotesParser parser = new VotesParser();
		return parser;
	}

	@Bean
	@Description("Acteurs parser")
	public ActeursParser createActeursParser() {
		ActeursParser parser = new ActeursParser();
		return parser;
	}

	@Bean
	@Description("Organes parser")
	public OrganesParser createOrganesParser() {
		OrganesParser parser = new OrganesParser();
		return parser;
	}
}
