package com.zhy_9.stoexpress;

import android.app.Activity;
import android.content.SharedPreferences;

import com.zhy_9.stoexpress.db.SQLManager;
import com.zhy_9.stoexpress.util.SharedPreferencesData;
import com.zhy_9.stoexpress.view.TitleView;

public class BaseActivity extends Activity {
	
	
	protected SQLManager sqlManager = null;
	protected SharedPreferences shared;
	
	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		sqlManager = new SQLManager(this);
		shared = SharedPreferencesData.getInstance(this);
	}
	
	protected TitleView title;
	
	

}
