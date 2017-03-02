package org.soframel.opendata.openpar.parsers.acteurs;

import java.time.LocalDate;
import java.util.List;

public class Mandat {

	enum TypeMandat {
		MandatParlementaire_type, MandatSimple_Type, MandatMission_Type, MandatAvecSuppleant_Type
	}

	private TypeMandat type;
	private String uid;
	private int legislature;
	private String organe;
	private LocalDate dateDebut;
	private LocalDate datePublication;
	private LocalDate dateFin;
	private String qualite;
	private List<String> organesRef;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public int getLegislature() {
		return legislature;
	}

	public void setLegislature(int legislature) {
		this.legislature = legislature;
	}

	public String getOrgane() {
		return organe;
	}

	public void setOrgane(String organe) {
		this.organe = organe;
	}

	public LocalDate getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(LocalDate dateDebut) {
		this.dateDebut = dateDebut;
	}

	public LocalDate getDatePublication() {
		return datePublication;
	}

	public void setDatePublication(LocalDate datePublication) {
		this.datePublication = datePublication;
	}

	public LocalDate getDateFin() {
		return dateFin;
	}

	public void setDateFin(LocalDate dateFin) {
		this.dateFin = dateFin;
	}

	public String getQualite() {
		return qualite;
	}

	public void setQualite(String qualite) {
		this.qualite = qualite;
	}

	public List<String> getOrganesRef() {
		return organesRef;
	}

	public void setOrganesRef(List<String> organesRef) {
		this.organesRef = organesRef;
	}

	@Override
	public String toString() {
		return "Mandat [uid=" + uid + ", legislature=" + legislature
				+ ", organe=" + organe + ", dateDebut=" + dateDebut
				+ ", datePublication=" + datePublication + ", dateFin="
				+ dateFin + ", qualite=" + qualite + ", organesRef="
				+ organesRef + "]";
	}

	public TypeMandat getType() {
		return type;
	}

	public void setType(TypeMandat type) {
		this.type = type;
	}

}
