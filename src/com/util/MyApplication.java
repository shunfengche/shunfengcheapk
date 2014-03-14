package com.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.app.po.Driver;
import com.app.po.User;

import android.app.Application;

public class MyApplication extends Application {
private User user=null,driver=null;
private List< Map<String,?>>listpipei=new ArrayList<Map<String,?>>();
private int port;
private Driver mydriver;
public static int myLongitudeE6;
public static int myLatitudeE6;
public User getUser() {
	return user;
}
public void setUser(User user) {
	this.user = user;
}
public User getDriver() {
	return driver;
}
public void setDriver(User driver) {
	this.driver = driver;
}
public List<Map<String, ?>> getListpipei() {
	return listpipei;
}
public void setListpipei(List<Map<String, ?>> listpipei) {
	this.listpipei = listpipei;
}
public void setPort(int port) {
	this.port = port;
}
public int getPort() {
	return port;
}
public void setMydriver(Driver mydriver) {
	this.mydriver = mydriver;
}
public Driver getMydriver() {
	return mydriver;
}

}
