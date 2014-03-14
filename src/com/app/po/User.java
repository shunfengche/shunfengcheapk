package com.app.po;

public class User {
private int userid;
private String username;
private String password;
private String telephone;
private String identitycard;
private String identitycardimgf;
private String identitycardimgb;
private float money;
private int state;
private String truename;
private String sex;
private String photo;
private String qianming;
public int getUserid() {
	return userid;
}
public void setUserid(int userid) {
	this.userid = userid;
}
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getTelephone() {
	return telephone;
}
public void setTelephone(String telephone) {
	this.telephone = telephone;
}
public String getIdentitycard() {
	return identitycard;
}
public void setIdentitycard(String identitycard) {
	this.identitycard = identitycard;
}
public String getIdentitycardimgf() {
	return identitycardimgf;
}
public void setIdentitycardimgf(String identitycardimgf) {
	this.identitycardimgf = identitycardimgf;
}
public String getIdentitycardimgb() {
	return identitycardimgb;
}
public void setIdentitycardimgb(String identitycardimgb) {
	this.identitycardimgb = identitycardimgb;
}
public float getMoney() {
	return money;
}
public void setMoney(float money) {
	this.money = money;
}
public int getState() {
	return state;
}
public void setState(int state) {
	this.state = state;
}
public String getTruename() {
	return truename;
}
public void setTruename(String truename) {
	this.truename = truename;
}
public String getSex() {
	return sex;
}
public void setSex(String sex) {
	this.sex = sex;
}
public String getPhoto() {
	return photo;
}
public void setPhoto(String photo) {
	this.photo = photo;
}
public String getQianming() {
	return qianming;
}
public void setQianming(String qianming) {
	this.qianming = qianming;
}
public User(int userid, String username, String password, String telephone,
		String identitycard, String identitycardimgf, String identitycardimgb,
		float money, int state, String truename, String sex, String photo,
		String qianming) {
	super();
	this.userid = userid;
	this.username = username;
	this.password = password;
	this.telephone = telephone;
	this.identitycard = identitycard;
	this.identitycardimgf = identitycardimgf;
	this.identitycardimgb = identitycardimgb;
	this.money = money;
	this.state = state;
	this.truename = truename;
	this.sex = sex;
	this.photo = photo;
	this.qianming = qianming;
}

}
