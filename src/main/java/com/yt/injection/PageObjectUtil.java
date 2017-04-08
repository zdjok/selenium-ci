package com.yt.injection;

import java.util.HashMap;

public class PageObjectUtil {
	private static HashMap<String,Object> pageObjs = null;

	@SuppressWarnings("unchecked")
	public static <T> T getPageObjs(String pageObjectClassName) {
		return (T) pageObjs.get(pageObjectClassName);
	}

	public static void setPageObjs(HashMap<String, Object> map) {
		pageObjs = map;
	}
	
}
