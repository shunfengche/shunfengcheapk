package com.app.po;

public class Line {
private int lineid;
private int userid;
private int seating;
private int seatleft;
private int state;
private String time;
private String speed;
private String  origin;
private String destination;
private int carlongitude;
private int carlatitude;
public int getLineid() {
	return lineid;
}
public void setLineid(int lineid) {
	this.lineid = lineid;
}
public int getUserid() {
	return userid;
}
public void setUserid(int userid) {
	this.userid = userid;
}
public int getSeating() {
	return seating;
}
public void setSeating(int seating) {
	this.seating = seating;
}
public int getSeatleft() {
	return seatleft;
}
public void setSeatleft(int seatleft) {
	this.seatleft = seatleft;
}
public int getState() {
	return state;
}
public void setState(int state) {
	this.state = state;
}
public String getTime() {
	return time;
}
public void setTime(String time) {
	this.time = time;
}
public String getSpeed() {
	return speed;
}
public void setSpeed(String speed) {
	this.speed = speed;
}
public String getOrigin() {
	return origin;
}
public void setOrigin(String origin) {
	this.origin = origin;
}
public String getDestination() {
	return destination;
}
public void setDestination(String destination) {
	this.destination = destination;
}
public int getCarlongitude() {
	return carlongitude;
}
public void setCarlongitude(int carlongitude) {
	this.carlongitude = carlongitude;
}
public int getCarlatitude() {
	return carlatitude;
}
public void setCarlatitude(int carlatitude) {
	this.carlatitude = carlatitude;
}
public Line(int lineid, int userid, int seating, int seatleft, int state,
		String time, String speed, String origin, String destination,
		int carlongitude, int carlatitude) {
	super();
	this.lineid = lineid;
	this.userid = userid;
	this.seating = seating;
	this.seatleft = seatleft;
	this.state = state;
	this.time = time;
	this.speed = speed;
	this.origin = origin;
	this.destination = destination;
	this.carlongitude = carlongitude;
	this.carlatitude = carlatitude;
}

}
