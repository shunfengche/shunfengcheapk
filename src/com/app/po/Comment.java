package com.app.po;

public class Comment {
   private int commentid;
   private int fromuserid;
   private int touserid;
   private int lineid;
   private String content;
   private String time;
public int getCommentid() {
	return commentid;
}
public void setCommentid(int commentid) {
	this.commentid = commentid;
}
public int getFromuserid() {
	return fromuserid;
}
public void setFromuserid(int fromuserid) {
	this.fromuserid = fromuserid;
}
public int getTouserid() {
	return touserid;
}
public void setTouserid(int touserid) {
	this.touserid = touserid;
}
public int getLineid() {
	return lineid;
}
public void setLineid(int lineid) {
	this.lineid = lineid;
}
public String getContent() {
	return content;
}
public void setContent(String content) {
	this.content = content;
}
public String getTime() {
	return time;
}
public void setTime(String time) {
	this.time = time;
}
public Comment(int commentid, int fromuserid, int touserid, int lineid,
		String content, String time) {
	super();
	this.commentid = commentid;
	this.fromuserid = fromuserid;
	this.touserid = touserid;
	this.lineid = lineid;
	this.content = content;
	this.time = time;
}
   
}
