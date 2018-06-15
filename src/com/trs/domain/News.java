package com.trs.domain;
/**
 * IR_HKEY  IR_URLNAME IR_SITENAME IR_CHANNEL IR_GROUPNAME IR_URLTITLE IR_URLDATE IR_URLTIME IR_LOADTIME IR_SRCNAME 
 * IR_KEYWORDS IR_ABSTRACT IR_CONTENT
 * @author xjb
 *
 */
public class News {
	
	private String IR_HKEY;
	private String IR_URLNAME;
	private  String IR_SITENAME;
	private String IR_CHANNEL;
	private String IR_GROUPNAME;
	
	private String IR_URLTITLE;
	private String IR_URLDATE;
	private String IR_URLTIME;
	private String IR_LOADTIME;
	private String IR_SRCNAME;
	private String IR_KEYWORDS;
	private String IR_ABSTRACT;
	private String IR_CONTENT;
	private String IR_AUTHORS;
	
	

	public String toTRS() {
		return "<REC>\n<IR_HKEY>=" + IR_HKEY + "\n<IR_URLNAME>=" + IR_URLNAME + "\n<IR_SITENAME>=" + IR_SITENAME
				+ "\n<IR_CHANNEL>=" + IR_CHANNEL + "\n<IR_GROUPNAME>=" + IR_GROUPNAME + "\n<IR_URLTITLE>=" + IR_URLTITLE
				+ "\n<IR_URLDATE>=" + IR_URLDATE + "\n<IR_URLTIME>=" + IR_URLTIME + "\n<IR_LOADTIME>=" + IR_LOADTIME
				+ "\n<IR_SRCNAME>=" + IR_SRCNAME + "\n<IR_KEYWORDS>=" + IR_KEYWORDS + "\n<IR_ABSTRACT>=" + IR_ABSTRACT
				+ "\n<IR_CONTENT>=" + IR_CONTENT + "\n<IR_AUTHORS>=" + IR_AUTHORS + "\n";
	}
	
	
	public String getIR_HKEY() {
		return IR_HKEY;
	}





	public void setIR_HKEY(String iR_HKEY) {
		IR_HKEY = iR_HKEY;
	}





	public String getIR_URLNAME() {
		return IR_URLNAME;
	}





	public void setIR_URLNAME(String iR_URLNAME) {
		IR_URLNAME = iR_URLNAME;
	}





	public String getIR_SITENAME() {
		return IR_SITENAME;
	}





	public void setIR_SITENAME(String iR_SITENAME) {
		IR_SITENAME = iR_SITENAME;
	}





	public String getIR_CHANNEL() {
		return IR_CHANNEL;
	}





	public void setIR_CHANNEL(String iR_CHANNEL) {
		IR_CHANNEL = iR_CHANNEL;
	}





	public String getIR_GROUPNAME() {
		return IR_GROUPNAME;
	}





	public void setIR_GROUPNAME(String iR_GROUPNAME) {
		IR_GROUPNAME = iR_GROUPNAME;
	}





	public String getIR_URLTITLE() {
		return IR_URLTITLE;
	}





	public void setIR_URLTITLE(String iR_URLTITLE) {
		IR_URLTITLE = iR_URLTITLE;
	}





	public String getIR_URLDATE() {
		return IR_URLDATE;
	}





	public void setIR_URLDATE(String iR_URLDATE) {
		IR_URLDATE = iR_URLDATE;
	}





	public String getIR_URLTIME() {
		return IR_URLTIME;
	}





	public void setIR_URLTIME(String iR_URLTIME) {
		IR_URLTIME = iR_URLTIME;
	}





	public String getIR_LOADTIME() {
		return IR_LOADTIME;
	}





	public void setIR_LOADTIME(String iR_LOADTIME) {
		IR_LOADTIME = iR_LOADTIME;
	}





	public String getIR_SRCNAME() {
		return IR_SRCNAME;
	}





	public void setIR_SRCNAME(String iR_SRCNAME) {
		IR_SRCNAME = iR_SRCNAME;
	}





	public String getIR_KEYWORDS() {
		return IR_KEYWORDS;
	}





	public void setIR_KEYWORDS(String iR_KEYWORDS) {
		IR_KEYWORDS = iR_KEYWORDS;
	}





	public String getIR_ABSTRACT() {
		return IR_ABSTRACT;
	}





	public void setIR_ABSTRACT(String iR_ABSTRACT) {
		IR_ABSTRACT = iR_ABSTRACT;
	}





	public String getIR_CONTENT() {
		return IR_CONTENT;
	}





	public void setIR_CONTENT(String iR_CONTENT) {
		IR_CONTENT = iR_CONTENT;
	}





	public String getIR_AUTHORS() {
		return IR_AUTHORS;
	}





	public void setIR_AUTHORS(String iR_AUTHORS) {
		IR_AUTHORS = iR_AUTHORS;
	}





	
	
}
