package com.yitu.entity;

public class User {

	private String phone;
	private String username;
	private String password;
	private int state; // 0是第一次登陆 为后期选择识别算法
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
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
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	@Override
	public String toString() {
		return "User [phone=" + phone + ", username=" + username + ", password=" + password + ", state=" + state + "]";
	}
}
