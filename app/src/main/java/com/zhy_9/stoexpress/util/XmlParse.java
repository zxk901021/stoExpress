package com.zhy_9.stoexpress.util;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;

import com.zhy_9.stoexpress.model.ProblemType;

import android.util.Log;
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
					Log.e("START_DOCUMENT", "START_DOCUMENT");
					break;

				case XmlPullParser.START_TAG:
					Log.e("START_TAG", "START_TAG");
					tagName = parser.getName();
					Log.e("tagName", tagName);
					if ("PROBLEMNO".equals(tagName)) {
						String problemNo = parser.nextText();
						Log.e("PROBLEMNO", problemNo);
						type = new ProblemType();
						type.setProblemNo(problemNo);
					}else if ("PROBLEMTYPE".equals(tagName)) {
						String problemType = parser.nextText();
						Log.e("PROBLEMTYPE", problemType);
						type.setProblemType(problemType);
					}else if ("TYPECODE".equals(tagName)) {
						String typeCode = parser.nextText();
						Log.e("TYPECODE", typeCode);
						type.setTypeCode(typeCode);
					}else if ("TYPE".equals(tagName)) {
						String typ = parser.nextText();
						Log.e("TYPE", typ);
						type.setType(typ);
					}else if ("ATTRIBUTE".equals(tagName)) {
						String attribute = parser.nextText();
						Log.e("ATTRIBUTE", attribute);
						type.setAttribute(attribute);
					}else if ("OPERFLAG".equals(tagName)) {
						String operflag = parser.nextText();
						Log.e("OPERFLAG", operflag);
						type.setOperflag(operflag);
					}else if ("LASTUPDATE".equals(tagName)) {
						String lastUpdate = parser.nextText();
						Log.e("LASTUPDATE", lastUpdate);
						type.setLastUpdate(lastUpdate);
					}
					break;
				case XmlPullParser.END_TAG:
					Log.e("END_TAG", parser.getName());
					if ("Row".equals(parser.getName())) {
						types.add(type);
						Log.e("typesµÄsize", types.size() + "");
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

}
