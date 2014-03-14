package com.app.po;

public class Driver {
   private int driverid;
   private int userid;
   private String drivinglicenceid;
   private String carnum;
   private String drivinglicenceimg;
   private String carimgi;
   private String carimgf;
   private String carimgs;
   private int zannum;
public int getDriverid() {
	return driverid;
}
public void setDriverid(int driverid) {
	this.driverid = driverid;
}
public int getUserid() {
	return userid;
}
public void setUserid(int userid) {
	this.userid = userid;
}
public String getDrivinglicenceid() {
	return drivinglicenceid;
}
public void setDrivinglicenceid(String drivinglicenceid) {
	this.drivinglicenceid = drivinglicenceid;
}
public String getCarnum() {
	return carnum;
}
public void setCarnum(String carnum) {
	this.carnum = carnum;
}
public String getDrivinglicenceimg() {
	return drivinglicenceimg;
}
public void setDrivinglicenceimg(String drivinglicenceimg) {
	this.drivinglicenceimg = drivinglicenceimg;
}
public String getCarimgi() {
	return carimgi;
}
public void setCarimgi(String carimgi) {
	this.carimgi = carimgi;
}
public String getCarimgf() {
	return carimgf;
}
public void setCarimgf(String carimgf) {
	this.carimgf = carimgf;
}
public String getCarimgs() {
	return carimgs;
}
public void setCarimgs(String carimgs) {
	this.carimgs = carimgs;
}
public int getZannum() {
	return zannum;
}
public void setZannum(int zannum) {
	this.zannum = zannum;
}
public Driver(int driverid, int userid, String drivinglicenceid, String carnum,
		String drivinglicenceimg, String carimgi, String carimgf,
		String carimgs, int zannum) {
	super();
	this.driverid = driverid;
	this.userid = userid;
	this.drivinglicenceid = drivinglicenceid;
	this.carnum = carnum;
	this.drivinglicenceimg = drivinglicenceimg;
	this.carimgi = carimgi;
	this.carimgf = carimgf;
	this.carimgs = carimgs;
	this.zannum = zannum;
}
   
}
