package com.zhy_9.stoexpress.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferencesData {
	
	private static final String PREFERENCE_NAME = "STO_PREFER";
	
	private static SharedPreferences preferences = null;
	
	private static Editor editor = null;
	

	public static SharedPreferences getInstance(Context context){
		if (preferences == null) {
			preferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
			return preferences;
		}
		return preferences;
	}
	
	public static Editor getEditor (Context context){
		if (editor == null) {
			editor = getInstance(context).edit();
			return editor;
		}
		return editor;
	}
	
	public static boolean putStoInfo(Context context, String key, String value){
		editor = getEditor(context);
		editor.putString(key, value);
		return editor.commit();
		
	}
	
	public static String getStoData(Context context, String key){
		String value = null;
		preferences = getInstance(context);
		value = preferences.getString(key, null);
		return value;
	}
	
	public static boolean deleteData(Context context, String key){
		editor = getEditor(context);
		editor.remove(key);
		return editor.commit();
		
	}
	
	public static boolean clearAllData (Context context) {
		editor = getEditor(context);
		editor.clear();
		return editor.commit();
	}
}
