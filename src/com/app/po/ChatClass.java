package com.app.po;

public class ChatClass {
 private int chatid;
 private int fromid;
 private int toid;
 private String chatcontent;
 private String time;
 private int flag;
 private int freeze;
 private int isread;
public int getChatid() {
	return chatid;
}
public void setChatid(int chatid) {
	this.chatid = chatid;
}
public int getFromid() {
	return fromid;
}
public void setFromid(int fromid) {
	this.fromid = fromid;
}
public int getToid() {
	return toid;
}
public void setToid(int toid) {
	this.toid = toid;
}
public String getChatcontent() {
	return chatcontent;
}
public void setChatcontent(String chatcontent) {
	this.chatcontent = chatcontent;
}
public String getTime() {
	return time;
}
public void setTime(String time) {
	this.time = time;
}
public int getFlag() {
	return flag;
}
public void setFlag(int flag) {
	this.flag = flag;
}
public int getFreeze() {
	return freeze;
}
public void setFreeze(int freeze) {
	this.freeze = freeze;
}
public ChatClass(int chatid, int fromid, int toid, String chatcontent, String time,
		int flag, int freeze,int isread) {
	super();
	this.chatid = chatid;
	this.fromid = fromid;
	this.toid = toid;
	this.chatcontent = chatcontent;
	this.time = time;
	this.flag = flag;
	this.freeze = freeze;
	this.isread=isread;
}
public void setIsread(int isread) {
	this.isread = isread;
}
public int getIsread() {
	return isread;
}
 
}
