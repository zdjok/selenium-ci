package com.yt.util.common;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.yt.io.ConfigIO;

public class ParseXml {
	static ConfigIO config = ConfigIO.getInstance();
	
	private static String testDataFileSuffix = ".xml";
	private static String dataNodeTag = "data-node";
	private static String dataNodeId  = "id";
	private static String dataTag = "data";
	private static String dataKey = "key";
	private static String dataValue = "value";
	
	public static HashMap<String,String> getNodeData(String dataNodeIds){
		if(dataNodeIds == null)
			return null;
		String[] dataNodeId = dataNodeIds.split(",");
		Set<String> nodeSet = new HashSet<String>();
		for(String nodeId : dataNodeId){
			nodeSet.add(nodeId);
		}
		
		HashMap<String,String> dataMap = new HashMap<String,String>();
		String testDataFilePath = config.getTestDataFilePath();
		File testDataPath = new File(testDataFilePath);
		File[] fs = testDataPath.listFiles();
		for(File f: fs){
			if(f.getName().endsWith(testDataFileSuffix))
				setDataMapFromFile(f, nodeSet, dataMap);
		}
		return dataMap;
	}
	
	@SuppressWarnings("unchecked")
	private static HashMap<String,String> setDataMapFromFile(File f, Set<String> nodeSet, HashMap<String,String> dataMap){
		SAXBuilder saxBuilder = new SAXBuilder();
		Document doc = null;
		try {
			doc = saxBuilder.build(f);
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Element root = doc.getRootElement();
		List<Element> eles = root.getChildren(dataNodeTag);
		for(Element e: eles){
			if(nodeSet.contains(e.getAttributeValue(dataNodeId))){
				List<Element> datas = e.getChildren(dataTag);
				for(Element data : datas){
					dataMap.put(data.getAttributeValue(dataKey), data.getAttributeValue(dataValue));
				}
			}
			nodeSet.remove(e.getAttributeValue(dataNodeId));
		}
		return dataMap;
	}
	
/*	@SuppressWarnings("unchecked")
	private static HashMap<String,String> setDataMapFromFile(File f, Set<String> nodeSet, HashMap<String,String> dataMap){
		SAXReader saxReader = new SAXReader();
		Document doc = null;
		try {
			doc = saxReader.read(f);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		Element root = doc.getRootElement();
		List<Element> eles = root.elements(dataNodeTag);
		for(Element e: eles){
			if(nodeSet.contains(e.attributeValue(dataNodeId))){
				List<Element> datas = e.elements(dataTag);
				for(Element data : datas){
					dataMap.put(data.attributeValue(dataKey), data.attributeValue(dataValue));
				}
			}
			nodeSet.remove(e.attributeValue(dataNodeId));
		}
		return dataMap;
	}*/

}
