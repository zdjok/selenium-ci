package com.yt.entity;

public class LoginEntity {
	private String url;
	private String j_username;
	private String j_password;

	public LoginEntity() {
		super();
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getJ_username() {
		return j_username;
	}

	public void setJ_username(String j_username) {
		this.j_username = j_username;
	}

	public String getJ_password() {
		return j_password;
	}

	public void setJ_password(String j_password) {
		this.j_password = j_password;
	}

	@Override
	public String toString() {
		return url + "?j_username=" + j_username + "&j_password=" + j_password;
	}

}
