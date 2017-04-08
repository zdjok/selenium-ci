package com.yt.util.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.openqa.selenium.Cookie;

public class HttpCore {
	static HttpClient httpClient;
	
	public static List<Cookie> doLoginAndGetCookies(String url){
		httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		List<Cookie> cookies = new ArrayList<Cookie>();
		try {
			HttpResponse httpResponse = httpClient.execute(httpPost);
			Header[] headers = httpResponse.getAllHeaders();
			for(Header h : headers){
				System.out.println("Header >>>>>>>>>>>>." + h );
				Cookie cookie = new Cookie(h.getName(), h.getValue());
				cookies.add(cookie);
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return cookies;
	}
	
	public static void doGet(String url){
		//to be done;
	}
	
	public static void main(String[] args) {
		String params = "j_username=admin&j_password=admin";
		List<Cookie> cookies = doLoginAndGetCookies("http://localhost:8080/jenkins/j_acegi_security_check?" + params);
		for(Cookie c : cookies){
			System.out.println(c.getName() + ": " + c.getValue());
		}
	}
}
