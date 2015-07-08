package com.zhy_9.stoexpress.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;

import com.zhy_9.stoexpress.model.ProblemType;
import com.zhy_9.stoexpress.model.StoInfo;

import android.util.Xml;


public class XmlParse {
	
	public static List<ProblemType> doParse (InputStream in) {
		List<ProblemType> types = null;
		ProblemType type = null;
		String tagName;
		XmlPullParser parser = Xml.newPullParser();
		try {
			parser.setInput(in, "UTF-8");
			int eventType = parser.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:
					types = new ArrayList<ProblemType>();
					break;

				case XmlPullParser.START_TAG:
					tagName = parser.getName();
					if ("PROBLEMNO".equals(tagName)) {
						String problemNo = parser.nextText();
						type = new ProblemType();
						type.setProblemNo(problemNo);
					}else if ("PROBLEMTYPE".equals(tagName)) {
						String problemType = parser.nextText();
						type.setProblemType(problemType);
					}else if ("TYPECODE".equals(tagName)) {
						String typeCode = parser.nextText();
						type.setTypeCode(typeCode);
					}else if ("TYPE".equals(tagName)) {
						String typ = parser.nextText();
						type.setType(typ);
					}else if ("ATTRIBUTE".equals(tagName)) {
						String attribute = parser.nextText();
						type.setAttribute(attribute);
					}else if ("OPERFLAG".equals(tagName)) {
						String operflag = parser.nextText();
						type.setOperflag(operflag);
					}else if ("LASTUPDATE".equals(tagName)) {
						String lastUpdate = parser.nextText();
						type.setLastUpdate(lastUpdate);
					}
					break;
				case XmlPullParser.END_TAG:
					if ("Row".equals(parser.getName())) {
						types.add(type);
					}
					
					break;
				}
				eventType = parser.next();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return types;
	}

	
	public static List<StoInfo> parseStoInfo(InputStream in){
		List<StoInfo> list = null;
		StoInfo info = null;
		String tagName;
		XmlPullParser parser = Xml.newPullParser();
		try {
			parser.setInput(in,"UTF-8");
			int eventType = parser.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:
					list = new ArrayList<StoInfo>();
					break;

				case XmlPullParser.START_TAG:
					tagName = parser.getName();
					if ("EMPLOYEENO".equals(tagName)) {
						String courierId = parser.nextText();
						info = new StoInfo();
						info.setCourierId(courierId);
					}else if ("EMPLOYEE".equals(tagName)) {
						String courierName = parser.nextText();
						info.setCourierName(courierName);
					}else if ("BELONGSITE".equals(tagName)) {
						String companyName = parser.nextText();
						info.setCompanyName(companyName);
					}else if ("AREACODE".equals(tagName)) {
						String branchNumber = parser.nextText();
						info.setBranchNumber(branchNumber);
					}else if ("PASSWORD".equals(tagName)) {
						String password = parser.nextText();
						info.setPassword(password);
					}else if ("LASTUPDATE".equals(tagName)) {
						String lastUpdate = parser.nextText();
						info.setLastUpdate(lastUpdate);
					}else if ("OPERFLAG".equals(tagName)) {
						String operflag = parser.nextText();
						info.setOperflag(operflag);
					}else if ("BELONGPOST".equals(tagName)) {
						String belongPost = parser.nextText();
						info.setBelongPost(belongPost);
					}
					break;
				case XmlPullParser.END_TAG:
					if ("Row".equals(parser.getName())) {
						list.add(info);
					}
					
					break;
				}
				eventType = parser.next();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
