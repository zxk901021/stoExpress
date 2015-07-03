package com.zhy_9.stoexpress;

import com.zhy_9.stoexpress.view.TitleView;

import android.os.Bundle;

public class SignInActivity extends BaseActivity {

	
	private TitleView title;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_in);
		
		initView();
	}

	private void initView(){
		title = (TitleView) findViewById(R.id.sign_in_title);
		title.setBackgroundColor(getResources().getColor(R.color.green));
		title.setTitle("зЂВс");
		title.setRightTextGone(true);
	}
	
}
