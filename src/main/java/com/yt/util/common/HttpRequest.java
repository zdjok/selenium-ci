package com.yt.util.common;

import java.util.List;

import org.openqa.selenium.Cookie;

import com.yt.entity.LoginEntity;
import com.yt.io.ConfigIO;

public class HttpRequest {
	static ConfigIO config = ConfigIO.getInstance();
	
	public static List<Cookie> doLogin(){
		LoginEntity loginEntity = new LoginEntity();
		loginEntity.setUrl(config.getLoginUrl());
		loginEntity.setJ_username(config.getUserName());
		loginEntity.setJ_password(config.getPassword());
		
		return HttpCore.doLoginAndGetCookies(loginEntity.toString());
	}
	
	
}
