package com.zhy_9.stoexpress;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.zhy_9.stoexpress.db.SQLManager;
import com.zhy_9.stoexpress.model.ConstansValues;
import com.zhy_9.stoexpress.model.ListDialogModel;
import com.zhy_9.stoexpress.model.ProblemType;
import com.zhy_9.stoexpress.model.StoInfo;
import com.zhy_9.stoexpress.util.AESEncrypt;
import com.zhy_9.stoexpress.util.SharedPreferencesData;
import com.zhy_9.stoexpress.util.WCFClient;
import com.zhy_9.stoexpress.util.XmlParse;
import com.zhy_9.stoexpress.view.ListDialog;
import com.zhy_9.stoexpress.view.TitleView;
import com.zhy_9.stoexpress.view.TitleView.RightBtnCallBack;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends BaseActivity implements OnClickListener {

	private TitleView title;

	private TextView delivery;
	private TextView problemShipment;
	private TextView signFor;
	private TextView scanRecord;
	private TextView uploadData;
	private String result;
	private StoInfo info;

	Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1:
				InputStream in = new ByteArrayInputStream(result.getBytes());
				List<ProblemType> list = new ArrayList<ProblemType>();
				Log.e("result", result);
				list = XmlParse.doParse(in);
				for (int i = 0, len = list.size(); i < len; i++) {
					sqlManager = new SQLManager(MainActivity.this);
					sqlManager.addProblemType(list.get(i));
				}
				break;

			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initView();
		widgetClick();
	}

	private void initView() {
		title = (TitleView) findViewById(R.id.saomiao_title);
		title.setBackgroundColor(getResources().getColor(R.color.yellow));
		title.setTitle(getResources().getString(R.string.baqiang));
		title.setRightText(getResources().getString(R.string.help));

		delivery = (TextView) findViewById(R.id.delivery);
		problemShipment = (TextView) findViewById(R.id.problem_shipment);
		signFor = (TextView) findViewById(R.id.sign_for);
		scanRecord = (TextView) findViewById(R.id.scan_record);
		uploadData = (TextView) findViewById(R.id.upload_data);
		info = new StoInfo("3000001", "苗元", "15123685978", "K56864565462",
				"天津公司");
		SharedPreferencesData
				.putStoInfo(this, "courierId", info.getCourierId());
		SharedPreferencesData.putStoInfo(this, "courierName",
				info.getCourierName());
		SharedPreferencesData.putStoInfo(this, "courierPhone",
				info.getCourierPhone());
		SharedPreferencesData.putStoInfo(this, "equipId", info.getEquipId());
		SharedPreferencesData.putStoInfo(this, "companyName",
				info.getCompanyName());
	}

	public void widgetClick() {
		delivery.setOnClickListener(this);
		problemShipment.setOnClickListener(this);
		signFor.setOnClickListener(this);
		scanRecord.setOnClickListener(this);
		uploadData.setOnClickListener(this);
		title.rightTextListener(new RightBtnCallBack() {

			@Override
			public void onRightBtnClick() {
				new Thread(new Runnable() {

					@Override
					public void run() {
						String time = WCFClient.getTime();
						Log.e("time", time);
						String transferParam = AESEncrypt
								.encrypt("{\"EnterpriseID\":\"TJSTO\","
										+ "\"StationID\":\"300000\","
										+ "\"PDAID\":\"869573010995351\","
										+ "\"Phone\":\"18920680203\","
										+ "\"EmpNo\":\"0001\","
										+ "\"Password\":\"7110\","
										+ "\"Version\":\"V1.0\","
										+ "\"DefaultLogic\":false,"
										+ "\"Compress\":true,\"FileType\":0}");
						// WCFClient.register(transferParam);
						String param = "{tab_problem_type_new_T_2015-04-11 18:32:57}";
						result = WCFClient.download(transferParam, param);
						handler.sendEmptyMessage(1);
					}
				}).start();

				List<ListDialogModel> data = new ArrayList<ListDialogModel>();
				for (int i = 0; i < 20; i++) {
					ListDialogModel model = new ListDialogModel();
					model.setListContent("测试数据" + i);
					// model.setIsChosen(i%2);
					model.setIsVisiable(1);
					data.add(model);
				}
				ListDialog dialog = new ListDialog(MainActivity.this, data,
						"选择问题类型", new ListDialog.ListDialogCallBack() {

							@Override
							public void getListContent(String content) {

							}
						});
				dialog.show();
			}
		});

	}

	public void jumpToActivity(int flag) {
		Intent intent = new Intent();
		if (flag == ConstansValues.SCAN_RECORD
				| flag == ConstansValues.UPLOAD_DATA) {
			intent.setClass(MainActivity.this, ScanRecordActivity.class);
		} else {
			intent.setClass(MainActivity.this, MipcaActivityCapture.class);
		}

		// intent.setClass(MainActivity.this, DeliveryActivity.class);
		// intent.setClass(MainActivity.this, ScanRecordActivity.class);
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

		case R.id.upload_data:
			if (sqlManager.queryUpload()) {
				jumpToActivity(ConstansValues.UPLOAD_DATA);
			} else {
				Toast.makeText(MainActivity.this, "没有可上传内容", Toast.LENGTH_SHORT)
						.show();
			}

			break;
		}
	}

}
