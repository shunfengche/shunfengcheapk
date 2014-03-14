package com.app.po;

public class Order {
private int orderid;
private int lineid;
private int userid;
private int state;
private String origin;
private String destination;
private int userlongitude;
private int userlatitude;
private float money;
private String time;
public int getOrderid() {
	return orderid;
}
public void setOrderid(int orderid) {
	this.orderid = orderid;
}
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
public int getState() {
	return state;
}
public void setState(int state) {
	this.state = state;
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
public int getUserlongitude() {
	return userlongitude;
}
public void setUserlongitude(int userlongitude) {
	this.userlongitude = userlongitude;
}
public int getUserlatitude() {
	return userlatitude;
}
public void setUserlatitude(int userlatitude) {
	this.userlatitude = userlatitude;
}
public float getMoney() {
	return money;
}
public void setMoney(float money) {
	this.money = money;
}

public void setTime(String time) {
	this.time = time;
}
public String getTime() {
	return time;
}
public Order(int orderid, int lineid, int userid, int state, String origin,
		String destination, int userlongitude, int userlatitude, float money,
		String time) {
	super();
	this.orderid = orderid;
	this.lineid = lineid;
	this.userid = userid;
	this.state = state;
	this.origin = origin;
	this.destination = destination;
	this.userlongitude = userlongitude;
	this.userlatitude = userlatitude;
	this.money = money;
	this.time = time;
}

}
