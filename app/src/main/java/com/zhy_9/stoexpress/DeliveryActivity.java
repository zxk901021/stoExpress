package com.zhy_9.stoexpress;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.zhy_9.stoexpress.model.ConstansValues;
import com.zhy_9.stoexpress.view.TitleView;

public class DeliveryActivity extends BaseActivity {

	private TitleView deliveryTitle;
	private int flag;
	private ListView deliveryList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_delivery);
		
		Intent intent = getIntent();
		flag = intent.getIntExtra(ConstansValues.BUTTON_FLAG, 0);
		initView();
	}

	public void initView(){
		deliveryTitle = (TitleView) findViewById(R.id.delivery_title);
		deliveryTitle.setTitle(titleValue(flag));
		deliveryTitle.setRightTextGone(true);
		deliveryTitle.setBackgroundColor(getResources().getColor(R.color.yellow));
		
		deliveryList = (ListView) findViewById(R.id.delivery_list);
	}
	
	public String titleValue(int flag){
		String titleStr = "";
		switch (flag) {
		case 1:
			titleStr = "派件扫描";
			return titleStr;

		case 2:
			titleStr = "问题件";
			return titleStr;
			
		case 3:
			titleStr = "签收扫描";
			return titleStr;
		}
		return titleStr;
	}
}
