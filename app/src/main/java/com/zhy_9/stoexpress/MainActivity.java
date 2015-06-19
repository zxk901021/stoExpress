package com.zhy_9.stoexpress;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.zhy_9.stoexpress.model.ConstansValues;
import com.zhy_9.stoexpress.model.ListDialogModel;
import com.zhy_9.stoexpress.view.ListDialog;
import com.zhy_9.stoexpress.view.TitleView;
import com.zhy_9.stoexpress.view.TitleView.RightBtnCallBack;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements OnClickListener{

	private TitleView title;


	private TextView delivery;
	private TextView problemShipment;
	private TextView signFor;
	private TextView scanRecord;
	private TextView uploadData;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initView();
		widgetClick();
	}

	private void initView(){
		title = (TitleView) findViewById(R.id.saomiao_title);
		title.setBackgroundColor(getResources().getColor(R.color.yellow));
		title.setTitle(getResources().getString(R.string.baqiang));
		title.setRightText(getResources().getString(R.string.help));

		delivery = (TextView) findViewById(R.id.delivery);
		problemShipment = (TextView) findViewById(R.id.problem_shipment);
		signFor = (TextView) findViewById(R.id.sign_for);
		scanRecord = (TextView) findViewById(R.id.scan_record);
		uploadData = (TextView) findViewById(R.id.upload_data);


	}


	public void widgetClick(){
		delivery.setOnClickListener(this);
		problemShipment.setOnClickListener(this);
		signFor.setOnClickListener(this);
		scanRecord.setOnClickListener(this);
		uploadData.setOnClickListener(this);
		title.rightTextListener(new RightBtnCallBack() {

			@Override
			public void onRightBtnClick() {
				List<ListDialogModel> data = new ArrayList<ListDialogModel>();
				for (int i = 0; i < 20; i++) {
					ListDialogModel model = new ListDialogModel();
					model.setListContent("测试数据" + i);
//					model.setIsChosen(i%2);
					model.setIsVisiable(1);
					data.add(model);
				}
				ListDialog dialog = new ListDialog(MainActivity.this, data);
				dialog.show();
			}
		});

	}

	public void jumpToActivity(int flag){
		Intent intent = new Intent();
		if (flag == ConstansValues.SCAN_RECORD) {
			intent.setClass(MainActivity.this, ScanRecordActivity.class);
		}else if (flag == ConstansValues.DELIVERY) {
			intent.setClass(MainActivity.this, MipcaActivityCapture.class);
		}else {
			intent.setClass(MainActivity.this, DeliveryActivity.class);
		}

//		intent.setClass(MainActivity.this, DeliveryActivity.class);
//		intent.setClass(MainActivity.this, ScanRecordActivity.class);
		intent.putExtra(ConstansValues.BUTTON_FLAG, flag);
		startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.delivery:
				jumpToActivity(ConstansValues.DELIVERY);
				break;

			case R.id.problem_shipment:
				jumpToActivity(ConstansValues.PROBLEM_SHIPMENT);
				break;

			case R.id.sign_for:
				jumpToActivity(ConstansValues.SIGN_FOR);
				break;

			case R.id.scan_record:
				jumpToActivity(ConstansValues.SCAN_RECORD);
				break;
		}
	}

}
