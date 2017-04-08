package com.yt.util.common;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class HtmlParser {
	private static final String CHARSET = "UTF-8";
	
	private Document doc;
	
	public HtmlParser(String html){
		this.doc = Jsoup.parse(html, CHARSET);
	}
	
	public Document getDocument(){
		return this.doc;
	}
	
	public Element getElementById(String id){
		Element ele = doc.getElementById(id);
		return ele;
	}
	
	public String getDocTitle(){
		return doc.title();
	}
	
	public String getValueByIdAndAttrName(String id, String attrName){
		Element ele = getElementById(id);
		String value = ele.attr(attrName);
		return value;
	}
	
	public Elements getElementsByClassName(String className){
		Elements eles = doc.getElementsByClass(className);
		return eles;
	}
	
	public List<String> getAttrValueListByClsAndAttrName(String className, String attrName){
		List<String> attrList = new ArrayList<String>();
		Elements eles = getElementsByClassName(className);
		if(eles.size() != 0 && eles != null)
			for(Element e : eles){
				if(e.hasAttr(attrName))
					attrList.add(e.attr(attrName));
			}
				
		return attrList;
	}
	
	public List<String> getAttrValueListByClsAndAttrNameWithSalt(String className, String attrName, String salt){
		List<String> attrList = new ArrayList<String>();
		Elements eles = getElementsByClassName(className);
		if(eles.size() != 0 && eles != null)
			for(Element e : eles){
				if(e.hasAttr(attrName) && e.hasAttr(salt))
					attrList.add(e.attr(attrName));
			}
				
		return attrList;
	}
	
	public List<String> getTxtListByClassName(String className){
		List<String> txtList = new ArrayList<String>();
		Elements eles = getElementsByClassName(className);
		if(eles.size() != 0 && eles != null)
			for(Element e : eles)
				txtList.add(e.text());
		return txtList;
	}
}
