package com.zhy_9.stoexpress;

import java.util.ArrayList;
import java.util.List;

import com.zhy_9.stoexpress.adapter.DeliveryAdapter;
import com.zhy_9.stoexpress.model.ConstansValues;
import com.zhy_9.stoexpress.model.ListDialogModel;
import com.zhy_9.stoexpress.model.ScanRecord;
import com.zhy_9.stoexpress.view.TitleView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

public class DeliveryActivity extends BaseActivity {

	private TitleView deliveryTitle;
	private int flag;
	private ListView deliveryList;
	private DeliveryAdapter adapter;
	private List<ScanRecord> data;
	private List<ListDialogModel> model;
	
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
		model = new ArrayList<ListDialogModel>();
		data = new ArrayList<ScanRecord>();
		for (int i = 0; i < 5; i++) {
			ScanRecord record = new ScanRecord();
			record.setExpressId("564886356554");
			record.setExpressStatus("最新状态：");
			record.setCourier("葛志中");
			data.add(record);
			ListDialogModel dialogModel = new ListDialogModel();
			dialogModel.setListContent("张三");
			model.add(dialogModel);
		}
		adapter = new DeliveryAdapter(this, data, model);
		deliveryList.setAdapter(adapter);
		
	}
	
	public String titleValue(int flag){
		String titleStr = "";
		switch (flag) {
		case 1:
			titleStr = getResources().getString(R.string.paijianshaomian);
			return titleStr;

		case 2:
			titleStr = getResources().getString(R.string.wentijian);
			return titleStr;
			
		case 3:
			titleStr = getResources().getString(R.string.qiaoshoushaomiao);
			return titleStr;
		}
		return titleStr;
	}
}
