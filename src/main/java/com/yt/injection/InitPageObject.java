package com.yt.injection;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import com.yt.io.ConfigIO;

public class InitPageObject {
	private static ConfigIO config = ConfigIO.getInstance();
	private static Object pageObj;
	
	private static String pageObjPath = config.getPageObjPath();
	private static String pageObjPkg = config.getPageObjPkg();
	private static Field[] fs;
	
	public InitPageObject(Object obj){
		pageObj = obj;
		PageObjectUtil.setPageObjs(initPageObject());
	}
	
	public HashMap<String, Object> initPageObject() {
		HashMap<String, Object> pageObjsMap = new HashMap<String, Object>();
		ArrayList<String> classNames = new ArrayList<String>();
		HashSet<String> fieldNameSet = new HashSet<String>();
		
		getPageObjectClass(pageObjPath, classNames);
		fs = pageObj.getClass().getDeclaredFields();
		fillFieldNameSet(fieldNameSet, fs);
		for(String clsName : classNames){
			try {
				Class<?> cls = Class.forName(clsName);
				if(cls.getAnnotation(PageObject.class) != null && fieldNameSet.contains(cls.getSimpleName())){
					System.out.println(">>>>>>>>>>>>>" + cls.getSimpleName() +">>>>>>>>>>>>" + pageObj);
					pageObjsMap.put(cls.getSimpleName(), cls.newInstance());
					System.out.println(">>>>>>>>>>>>>>" + pageObjsMap);
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		
		injectPageObj(fs, fieldNameSet, pageObjsMap);

		return pageObjsMap;
	}
	

	private void injectPageObj(Field[] fs, HashSet<String> fieldNameSet, HashMap<String, Object> pageObjsMap) {
		for(Field f : fs){
			if(fieldNameSet.contains(f.getType().getSimpleName())){
				f.setAccessible(true);
				try {
					f.set(pageObj, pageObjsMap.get(f.getType().getSimpleName()));
					System.out.println(pageObj);
					System.out.println(">>>>>>>>>>>>>>>>>>>" + pageObjsMap.get(f.getType().getSimpleName()));
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				f.setAccessible(false);
			}
		}
	}

	private void fillFieldNameSet(HashSet<String> fnSet, Field[] fs) {
		for(Field f : fs){
			if(f.getAnnotation(AutoInject.class) != null){
				fnSet.add(f.getType().getSimpleName());
			}
		}
	}

	private void getPageObjectClass(String pageObjPath, ArrayList<String> classNames){
		File dir = new File(pageObjPath);
		File[] fs = dir.listFiles();
		for(File f : fs){
			if(f.isFile() && f.getName().endsWith(".java")){
				classNames.add(pageObjPkg + "." + f.getName().substring(0, f.getName().indexOf(".")));
			}
		}
	}
	
	
	public static void main(String[] args) {
		Field[] fs = InitPageObject.class.getDeclaredFields();
		for(Field f : fs){
			System.out.println(f.getType().getSimpleName());
		}
	}
}
