package org.soframel.opendata.openpar.parsers.scrutins;

import java.time.LocalDate;
import java.util.ArrayDeque;

import org.soframel.opendata.openpar.domain.frpar.ModePublicationVotes;
import org.soframel.opendata.openpar.domain.frpar.PositionVote;
import org.soframel.opendata.openpar.domain.frpar.Scrutin;
import org.soframel.opendata.openpar.domain.frpar.TypeVote;
import org.soframel.opendata.openpar.domain.frpar.VotesGroupe;
import org.soframel.opendata.openpar.parsers.AbstractContentHandler;
import org.soframel.opendata.openpar.repository.ScrutinRepository;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class ScrutinsContentHandler extends AbstractContentHandler {

	private Scrutin scrutin;

	private ScrutinRepository scrutinRepository;

	private VotesGroupe currentGroupe;

	public ScrutinsContentHandler(ScrutinRepository repository) {
		this.scrutinRepository = repository;
		namesStack = new ArrayDeque<>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		currentCharacters = new StringBuffer();
		namesStack.push(localName);
		if (localName.equals("scrutin")) {
			log.info("parsing scrutin");
			scrutin = new Scrutin();
		} else if (localName.equals("groupe")) {
			currentGroupe = new VotesGroupe();
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		String currentElementName = namesStack.pop();
		String previousElementName = namesStack.peek();

		if (localName.equals("scrutin")) {
			log.info("inserting scrutin " + scrutin.getUid());
			scrutinRepository.insert(scrutin);
		} else if (localName.equals("groupe")) {
			scrutin.getVotesGroupes().add(currentGroupe);
			currentGroupe = null;
		} else if ("scrutin".equals(previousElementName)) {
			if (localName.equals("uid")) {
				scrutin.setUid(currentCharacters.toString());
			} else if (localName.equals("organeRef")) {
				scrutin.setOrganeRef(currentCharacters.toString());
			} else if (localName.equals("dateScrutin")) {
				LocalDate date = LocalDate.parse(currentCharacters.toString(),
						formatter);
				scrutin.setDateScrutin(date);
			} else if (localName.equals("titre")) {
				scrutin.setTitre(removeNewLinesDoubleWhitespaces(currentCharacters
						.toString()));
			} else if (localName.equals("modePublicationDesVotes")) {
				scrutin.setModePublicationVotes(ModePublicationVotes
						.valueOf(currentCharacters.toString()));
			}
		} else if ("typeVote".equals(previousElementName)) {
			if (localName.equals("codeTypeVote")) {
				scrutin.setTypeVote(TypeVote.valueOf(currentCharacters
						.toString()));
			}
		} else if ("sort".equals(previousElementName)) {
			if (localName.equals("code")) {
				String code = currentCharacters.toString();
				scrutin.setAdopte("adopté".equals(code));
			}
		} else if ("demandeur".equals(previousElementName)) {
			if (localName.equals("texte")) {
				scrutin.setDemandeur(currentCharacters.toString());
			}
		} else if ("objet".equals(previousElementName)) {
			if (localName.equals("libelle")) {
				scrutin.setObjet(removeNewLinesDoubleWhitespaces(currentCharacters
						.toString()));
			}
		} else if ("syntheseVote".equals(previousElementName)) {
			if (localName.equals("nombreVotants")) {
				scrutin.setNbVotants(Integer.parseInt(currentCharacters
						.toString()));
			} else if (localName.equals("suffragesExprimes")) {
				scrutin.setSuffragesExprimes(Integer.parseInt(currentCharacters
						.toString()));
			} else if (localName.equals("nbrSuffragesRequis")) {
				scrutin.setSuffragesRequis(Integer.parseInt(currentCharacters
						.toString()));
			}
		} else if ("decompte".equals(previousElementName)) {
			if (localName.equals("pour")) {
				scrutin.setVotesPour(Integer.parseInt(currentCharacters
						.toString()));
			} else if (localName.equals("contre")) {
				scrutin.setVotesContre(Integer.parseInt(currentCharacters
						.toString()));
			} else if (localName.equals("abstention")) {
				scrutin.setAbstention(Integer.parseInt(currentCharacters
						.toString()));
			} else if (localName.equals("nonVotant")) {
				scrutin.setNonVotants(Integer.parseInt(currentCharacters
						.toString()));
			}
		} else if ("groupe".equals(previousElementName)) {
			if (localName.equals("organeRef")) {
				currentGroupe.setOrganeRef(currentCharacters.toString());
			} else if (localName.equals("nombreMembresGroupe")) {
				currentGroupe.setNbMembresGroupe(Integer
						.parseInt(currentCharacters.toString()));
			}
		} else if ("vote".equals(previousElementName)) {
			if (localName.equals("positionMajoritaire")) {
				currentGroupe.setPositionMajoritaire(PositionVote
						.valueOf(currentCharacters.toString().toUpperCase()));
			}
		} else if ("decompteVoix".equals(previousElementName)) {
			if (localName.equals("pour")) {
				currentGroupe.setVotesPour(Integer.parseInt(currentCharacters
						.toString()));
			} else if (localName.equals("contre")) {
				currentGroupe.setVotesContre(Integer.parseInt(currentCharacters
						.toString()));
			} else if (localName.equals("abstention")) {
				currentGroupe.setNbAbstentions(Integer
						.parseInt(currentCharacters.toString()));
			} else if (localName.equals("nonVotant")) {
				currentGroupe.setNbNonVotants(Integer
						.parseInt(currentCharacters.toString()));
			}
		}

		currentCharacters = new StringBuffer();
	}

}
