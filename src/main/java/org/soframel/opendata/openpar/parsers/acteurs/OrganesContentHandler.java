package org.soframel.opendata.openpar.parsers.acteurs;

import java.time.LocalDate;
import java.util.ArrayDeque;

import org.soframel.opendata.openpar.parsers.AbstractContentHandler;
import org.soframel.opendata.openpar.parsers.acteurs.Organe.CodeType;
import org.soframel.opendata.openpar.parsers.acteurs.Organe.TypeOrgane;
import org.soframel.opendata.openpar.repository.OrganeRepository;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

public class OrganesContentHandler extends AbstractContentHandler {

	private Organe organe;

	private OrganeRepository OrganeRepository;

	public OrganesContentHandler(OrganeRepository repository) {
		this.OrganeRepository = repository;
		namesStack = new ArrayDeque<>();
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		currentCharacters = new StringBuffer();
		namesStack.push(localName);
		if (localName.equals("organe")) {
			log.info("parsing organe");
			organe = new Organe();
			if (attributes.getLength() > 0) {
				organe.setType(TypeOrgane.valueOf(attributes.getValue(0)));
			}
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		String currentElementName = namesStack.pop();
		String previousElementName = namesStack.peek();

		if (localName.equals("organe")) {
			log.info("inserting Organe " + organe.getUid());
			OrganeRepository.insert(organe);
		} else if ("organe".equals(previousElementName)) {
			if (localName.equals("uid")) {
				organe.setUid(currentCharacters.toString());
			} else if (localName.equals("codeType")) {
				organe.setCodeType(CodeType.valueOf(currentCharacters
						.toString()));
			} else if (localName.equals("libelle")) {
				organe.setLibelle(currentCharacters.toString());
			} else if (localName.equals("libelleEdition")) {
				organe.setLibelleEdition(removeNewLinesDoubleWhitespaces(currentCharacters
						.toString()));
			} else if (localName.equals("libelleAbrege")) {
				organe.setLibelleAbrege(currentCharacters.toString());
			} else if (localName.equals("libelleAbrev")) {
				organe.setLibelleAbrev(currentCharacters.toString());
			} else if (localName.equals("regime")) {
				organe.setRegime(currentCharacters.toString());
			} else if (localName.equals("legislature")) {
				organe.setLegislature(Integer.valueOf(currentCharacters
						.toString()));
			}
		} else if ("viMoDe".equals(previousElementName)) {
			if (localName.equals("dateDebut") && currentCharacters.length() > 0) {
				organe.setDateDebut(LocalDate.parse(
						currentCharacters.toString(), formatter));
			} else if (localName.equals("dateAgrement")
					&& currentCharacters.length() > 0) {
				organe.setDateAgrement(LocalDate.parse(
						currentCharacters.toString(), formatter));
			} else if (localName.equals("dateFin")
					&& currentCharacters.length() > 0) {
				organe.setDateFin(LocalDate.parse(currentCharacters.toString(),
						formatter));
			}
		}
		currentCharacters = new StringBuffer();
	}

}
