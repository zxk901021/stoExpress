package com.zhy_9.stoexpress;

import com.zhy_9.stoexpress.db.SQLManager;
import com.zhy_9.stoexpress.util.SharedPreferencesData;
import com.zhy_9.stoexpress.view.TitleView;

import android.app.Activity;
import android.content.SharedPreferences;

public class BaseActivity extends Activity {
	
	
	protected SQLManager sqlManager = null;
	
	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		sqlManager = new SQLManager(this);
	}
	
	protected TitleView title;
	
	

}
