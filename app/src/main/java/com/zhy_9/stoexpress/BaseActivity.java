package com.zhy_9.stoexpress;

import android.app.Activity;

import com.zhy_9.stoexpress.db.SQLManager;
import com.zhy_9.stoexpress.view.TitleView;

public class BaseActivity extends Activity {


	protected SQLManager sqlManager = null;

	@Override
	public void setContentView(int layoutResID) {
		super.setContentView(layoutResID);
		sqlManager = SQLManager.getInstance(this);

	}

	protected TitleView title;



}
