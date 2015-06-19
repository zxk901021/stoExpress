package com.zhy_9.stoexpress.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

public class CommonUtil {

	public static float getDensity(Context context){
		Resources resources = context.getResources();
		DisplayMetrics dm = resources.getDisplayMetrics();
		return dm.density;
	}
	
}
