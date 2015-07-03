package com.zhy_9.stoexpress.util;

import android.content.Context;
import android.content.res.Resources;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

public class CommonUtil {

	public static float getDensity(Context context) {
		Resources resources = context.getResources();
		DisplayMetrics dm = resources.getDisplayMetrics();
		return dm.density;
	}

	public static String getProblemType(){
		String result = null;
		String transferParam = AESEncrypt
				.encrypt("{\"EnterpriseID\":\"TJSTO\","
						+ "\"StationID\":\"300000\","
						+ "\"PDAID\":\"869573010995351\","
						+ "\"Phone\":\"13700201234\","
						+ "\"EmpNo\":\"0001\","
						+ "\"Password\":\"1234\","
						+ "\"Version\":\"V1.0\","
						+ "\"DefaultLogic\":false,"
						+ "\"Compress\":true,\"FileType\":0}");
		String param = "{tab_problem_type_new_T_2015-06-08 10:22:02}";
		result = WCFClient.download(transferParam, param);
		return result;
	}
	
	public static String getImei(Context context){
		TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		return manager.getDeviceId();
	}
	

	
}
