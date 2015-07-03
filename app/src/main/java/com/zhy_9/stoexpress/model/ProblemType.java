package com.zhy_9.stoexpress.model;

public class ProblemType {

	private String problemNo;
	private String problemType;
	private String typeCode;
	private String type;
	private String attribute;
	private String operflag;
	private String lastUpdate;

	public ProblemType() {
		super();
	}

	public String getProblemNo() {
		return problemNo;
	}

	public void setProblemNo(String problemNo) {
		this.problemNo = problemNo;
	}

	public String getProblemType() {
		return problemType;
	}

	public void setProblemType(String problemType) {
		this.problemType = problemType;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAttribute() {
		return attribute;
	}

	public void setAttribute(String attribute) {
		this.attribute = attribute;
	}

	public String getOperflag() {
		return operflag;
	}

	public void setOperflag(String operflag) {
		this.operflag = operflag;
	}

	public String getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	@Override
	public String toString() {
		return "problemNo:" + problemNo + "<br>problemType:" + problemType
				+ "<br>typeCode:" + typeCode + "<br>type" + type
				+ "<br>attribute:" + attribute + "<br>operflag" + operflag
				+ "<br>lastUpdate" + lastUpdate;
	}
}
