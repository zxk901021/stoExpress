package com.zhy_9.stoexpress.util;

import android.content.Context;
import android.content.res.Resources;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

public class CommonUtil {

	public static float getDensity(Context context) {
		Resources resources = context.getResources();
		DisplayMetrics dm = resources.getDisplayMetrics();
		return dm.density;
	}

	public static String getProblemType() {
		String result = null;
		String transferParam = AESEncrypt
				.encrypt("{\"EnterpriseID\":\"TJSTO\","
						+ "\"StationID\":\"300000\","
						+ "\"PDAID\":\"869573010995351\","
						+ "\"Phone\":\"13700201234\"," + "\"EmpNo\":\"0001\","
						+ "\"Password\":\"1234\"," + "\"Version\":\"V1.0\","
						+ "\"DefaultLogic\":false,"
						+ "\"Compress\":true,\"FileType\":0}");
		String param = "{tab_problem_type_new_T_2015-06-08 10:22:02}";
		result = WCFClient.download(transferParam, param);
		return result;
	}

	public static String getImei(Context context) {
		TelephonyManager manager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return manager.getDeviceId();
	}

	public static void copyDB() {
		String dbName = "sto_express.db";
		String path = "data/data/com.zhy_9.stoexpress/databases/" + dbName;
		String filePath = Environment.getExternalStorageDirectory()
				+ File.separator + dbName;
		copyFile(path, filePath);
	}

	public static void copyFile(String dbPath, String filePath) {
		try {
			int byteread = 0;
			File oldfile = new File(dbPath);
			File newfile = new File(filePath);
			if (!newfile.exists()) {
				newfile.createNewFile();
			}
			if (oldfile.exists()) { // 文件存在时
				InputStream inStream = new FileInputStream(dbPath); // 读入原文件
				FileOutputStream fs = new FileOutputStream(filePath);
				byte[] buffer = new byte[1444];
				while ((byteread = inStream.read(buffer)) != -1) {
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
				fs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
