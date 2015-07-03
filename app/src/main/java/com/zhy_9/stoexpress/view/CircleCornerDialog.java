package com.zhy_9.stoexpress.view;

import com.zhy_9.stoexpress.util.CommonUtil;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class CircleCornerDialog extends Dialog {

	private static int default_width = 160;
	private static int default_height = 120;

	public CircleCornerDialog(Context context, View layout, int style) {
		this(context, default_width, default_height, layout, style);
	}

	public CircleCornerDialog(Context context, int width, int height,
			View layout, int style) {
		super(context, style);
		setContentView(layout);
		Window window = getWindow();
		WindowManager.LayoutParams params = window.getAttributes();
		float density = CommonUtil.getDensity(context);
		Log.e("density", density + "");
		params.width = (int) (width * density);
		params.height = (int) (height * density);
		params.gravity = Gravity.CENTER;
		window.setAttributes(params);

	}

}
