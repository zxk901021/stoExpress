package com.zhy_9.stoexpress.util;

import java.io.IOException;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;

import android.util.Log;


public class WCFClient {
	
	public static String NameSpace = "http://tempuri.org/";
	public static String URL = "http://60.30.135.94:838/PdaTransfer.svc";
	public static String SOAP_ACTION = "http://tempuri.org/IPdaTransfer/";
	
	
	public static String register(String registerInfo) {
		StringBuilder responseStr = new StringBuilder();
		String propertyName = "RegisterResult";
		String methodName = "Register";
		String action = SOAP_ACTION + methodName;
		PropertyInfo requestProperty = new PropertyInfo();
		requestProperty.setName("regsiterInfo");
		requestProperty.setValue(registerInfo);
		PropertyInfo[] params = { requestProperty };
		boolean flag = getRequesData(methodName, action, params, propertyName,
				responseStr);
		String result = "";
		if (flag) {
			result = responseStr.toString();
		}else {
			result = "";
		}
		return result;
	}
	
	public static String getTime(){
		StringBuilder responseStr = new StringBuilder();
		String propertyName = "ServerTimeResult";
		String methodName = "ServerTime";
		String action = SOAP_ACTION + methodName;
		boolean flag = getRequesData(methodName, action, null, propertyName, responseStr);
		String result = "";
		if (flag) {
			result = responseStr.toString();
		}else {
			result = "";
		}
		
		return result;
	}
	
	public static String download(String transfer, String param){
		StringBuilder responStr = new StringBuilder();
		String propertyName = "DownloadResult";
		String methodName = "Download";
		String action = SOAP_ACTION + methodName;
		PropertyInfo requestProperty = new PropertyInfo();
		requestProperty.setName("transferParam");
		requestProperty.setValue(transfer);
		PropertyInfo requestProperty1 = new PropertyInfo();
		requestProperty1.setName("param");
		requestProperty1.setValue(param);
		PropertyInfo[] params = {requestProperty, requestProperty1};
		boolean flag = getRequesData(methodName, action, params, propertyName, responStr);
		String result = "";
		
		if (flag) {
			result = responStr.toString();
		}else {
			result = "";
		}
		String temp = GZipUtil.gZipUnString(result);
		
		Log.e("temp", temp);
		return temp;
	}
	
	
	public static String upload (String transfer, String content){
		StringBuilder responStr = new StringBuilder();
		String propertyName = "UploadResult";
		String methodName = "Upload";
		String action = SOAP_ACTION + methodName;
		PropertyInfo requestProperty = new PropertyInfo();
		requestProperty.setName("transferParam");
		requestProperty.setValue(transfer);
		PropertyInfo requestProperty1 = new PropertyInfo();
		requestProperty1.setName("content");
		requestProperty1.setValue(content);
		PropertyInfo[] params = { requestProperty, requestProperty1 };
		boolean flag = getRequesData(methodName, action, params, propertyName,
				responStr);
		String result = "";
		if (flag) {
			result = responStr.toString();
		}else {
			result = "";
		}

		Log.e("temp  upload", result);
		return result;
	}

	public static boolean getRequesData(String methodName, String soap_action,
			PropertyInfo[] proInfo, String responsePropetryName,
			StringBuilder response) {
		boolean flag = false;
		SoapObject soapObject = new SoapObject(NameSpace, methodName);
		// 添加参数
		if (proInfo != null) {
			for (int i = 0; i < proInfo.length; i++) {
				soapObject.addProperty(proInfo[i]);
			}
		}
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
				SoapEnvelope.VER11);
		envelope.bodyOut = soapObject;
		envelope.dotNet = true;
		envelope.setOutputSoapObject(soapObject);

		HttpTransportSE trans = new HttpTransportSE(URL);
		trans.debug = true;
		try {
			trans.call(soap_action, envelope);
			flag = true;
		} catch (HttpResponseException e) {
			response.append("网络或服务端异常");
		} catch (IOException e) {
			response.append("网络或服务端异常");
		} catch ( Exception e) {
			e.printStackTrace();
		}
		try {
			SoapObject soapObj = null;
			Object objResponse=envelope.getResponse();
			if (flag == true && objResponse!= null) {
				soapObj = (SoapObject) envelope.bodyIn;
				Object obj = soapObj.getProperty(responsePropetryName);
				response.append(obj.toString());
			}
		} catch (Exception e) {
			flag = false;
			response.append(e.getMessage());
		}
		return flag;
	}
	
}
