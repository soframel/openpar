package org.soframel.opendata.openpar.parsers.scrutins;

public class Vote {
	enum CausePositionVote {
		MG, PSE, PAN
	}

	private String voteId;

	private String acteurRef;
	private String mandatRef;
	private PositionVote vote;
	private CausePositionVote causePositionVote;
	private String scrutinId;

	public void generateVoteId() {
		voteId = scrutinId + "-" + acteurRef;
	}

	public String getActeurRef() {
		return acteurRef;
	}

	public void setActeurRef(String acteurRef) {
		this.acteurRef = acteurRef;
	}

	public String getMandatRef() {
		return mandatRef;
	}

	public void setMandatRef(String mandatRef) {
		this.mandatRef = mandatRef;
	}

	public PositionVote getVote() {
		return vote;
	}

	public void setVote(PositionVote vote) {
		this.vote = vote;
	}

	public CausePositionVote getCausePositionVote() {
		return causePositionVote;
	}

	public void setCausePositionVote(CausePositionVote causePositionVote) {
		this.causePositionVote = causePositionVote;
	}

	@Override
	public String toString() {
		return "Vote [acteurRef=" + acteurRef + ", mandatRef=" + mandatRef
				+ ", vote=" + vote + ", causePositionVote=" + causePositionVote
				+ "]";
	}

	public String getVoteId() {
		return voteId;
	}

	public String getScrutinId() {
		return scrutinId;
	}

	public void setScrutinId(String scrutinId) {
		this.scrutinId = scrutinId;
	}

}
