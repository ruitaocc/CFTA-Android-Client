package com.ClassTransaction.client;

public class Course{
	private String ID;
	private String COURSENAME;
	private String COURSETIME;
	private String COURSEPLACE;
	private String COURSEMASTERID;
	private String COURSECREDIT;
	private String COURSETYPE;
	private String COURSEREMARK;
	private String ISDELETE;
	
	public String getCOURSENAME() {
		return COURSENAME;
	}
	public void setCOURSENAME(String cOURSENAME) {
		COURSENAME = cOURSENAME;
	}
	public String getCOURSETIME() {
		return COURSETIME;
	}
	public void setCOURSETIME(String cOURSETIME) {
		COURSETIME = cOURSETIME;
	}
	public String getCOURSEPLACE() {
		return COURSEPLACE;
	}
	public void setCOURSEPLACE(String cOURSEPLACE) {
		COURSEPLACE = cOURSEPLACE;
	}
	public String getCOURSEMASTERID() {
		return COURSEMASTERID;
	}
	public void setCOURSEMASTERID(String cOURSEMASTERID) {
		COURSEMASTERID = cOURSEMASTERID;
	}
	public String getCOURSECREDIT() {
		return COURSECREDIT;
	}
	public void setCOURSECREDIT(String cOURSECREDIT) {
		COURSECREDIT = cOURSECREDIT;
	}
	public String getCOURSETYPE() {
		return COURSETYPE;
	}
	public void setCOURSETYPE(String cOURSETYPE) {
		COURSETYPE = cOURSETYPE;
	}
	public String getCOURSEREMARK() {
		return COURSEREMARK;
	}
	public void setCOURSEREMARK(String cOURSEREMARK) {
		COURSEREMARK = cOURSEREMARK;
	}
	
	public String getISDELETE() {
		return ISDELETE;
	}
	public void setISDELETE(String iSDELETE) {
		ISDELETE = iSDELETE;
	}
	
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	@Override
	public String toString() {
		return "Course [COURSECREDIT=" + COURSECREDIT + ", COURSEMASTERID="
				+ COURSEMASTERID + ", COURSENAME=" + COURSENAME
				+ ", COURSEPLACE=" + COURSEPLACE + ", COURSEREMARK="
				+ COURSEREMARK + ", COURSETIME=" + COURSETIME + ", COURSETYPE="
				+ COURSETYPE + ", ID=" + ID + ", ISDELETE=" + ISDELETE + "]";
	}
	
}
