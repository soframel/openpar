package org.soframel.opendata.openpar.parsers.scrutins;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayDeque;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soframel.opendata.openpar.domain.frpar.CausePositionVote;
import org.soframel.opendata.openpar.domain.frpar.PositionVote;
import org.soframel.opendata.openpar.domain.frpar.Vote;
import org.soframel.opendata.openpar.parsers.AbstractContentHandler;
import org.soframel.opendata.openpar.repository.ScrutinRepository;
import org.soframel.opendata.openpar.repository.VoteRepository;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class VotesContentHandler extends AbstractContentHandler {

	private Vote vote;

	private VoteRepository repository;
	private String scrutinId;
	private PositionVote currentPosition;

	public VotesContentHandler(VoteRepository repository) {
		this.repository = repository;
		namesStack = new ArrayDeque<>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		currentCharacters = new StringBuffer();
		namesStack.push(localName);
		if (localName.equals("votant")) {
			vote = new Vote();
			vote.setScrutinId(scrutinId);
			vote.setVote(currentPosition);
		} else if (localName.equals("nonVotants")) {
			this.currentPosition = PositionVote.NONVOTANT;
		} else if (localName.equals("pours")) {
			this.currentPosition = PositionVote.POUR;
		} else if (localName.equals("contres")) {
			this.currentPosition = PositionVote.CONTRE;
		} else if (localName.equals("abstentions")) {
			this.currentPosition = PositionVote.ABSTENTION;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		namesStack.pop();
		String previousElementName = namesStack.peek();

		if (localName.equals("votant")) {
			vote.generateVoteId();
			log.info("inserting vote " + vote.getVoteId());
			repository.insert(vote);
		} else if ("scrutin".equals(previousElementName)) {
			if (localName.equals("uid")) {
				this.scrutinId = currentCharacters.toString();
			}
		} else if ("votant".equals(previousElementName)) {
			if (localName.equals("acteurRef")) {
				vote.setActeurRef(currentCharacters.toString());
			} else if (localName.equals("mandatRef")) {
				vote.setMandatRef(currentCharacters.toString());
			} else if (localName.equals("causePositionVote")) {
				vote.setCausePositionVote(CausePositionVote
						.valueOf(currentCharacters.toString()));
			}
		}

		currentCharacters = new StringBuffer();
	}

}
