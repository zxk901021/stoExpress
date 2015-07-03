package com.zhy_9.stoexpress;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.zhy_9.stoexpress.adapter.DeliveryAdapter;
import com.zhy_9.stoexpress.model.ConstansValues;
import com.zhy_9.stoexpress.model.ListDialogModel;
import com.zhy_9.stoexpress.model.ProblemType;
import com.zhy_9.stoexpress.model.ScanRecord;
import com.zhy_9.stoexpress.util.AESEncrypt;
import com.zhy_9.stoexpress.util.SharedPreferencesData;
import com.zhy_9.stoexpress.view.DrawableCenterTextView;
import com.zhy_9.stoexpress.view.TitleView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DeliveryActivity extends BaseActivity implements OnClickListener {

	private TitleView deliveryTitle;
	private int flag;
	private ListView deliveryList;
	private DeliveryAdapter adapter;
	private List<ScanRecord> data;
	private List<ListDialogModel> model;
	private List<ProblemType> type;
	private String result;
	private DrawableCenterTextView midTitle;
	private DrawableCenterTextView save, upload;
	private String dates, times, year;

	public static final String UPLOAD_DONE = "1";
	public static final String UPLOAD_NOT = "0";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_delivery);

		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat format2 = new SimpleDateFormat("HH:mm:ss");
		SimpleDateFormat format3 = new SimpleDateFormat("yyyy");
		dates = format.format(date);
		times = format2.format(date);
		year = format3.format(date);
		Intent intent = getIntent();
		flag = intent.getIntExtra(ConstansValues.BUTTON_FLAG, 0);
		result = intent.getStringExtra("result");
		initView();
	}

	public String setMidTitle(int flag) {
		String title = "";
		if (flag == ConstansValues.DELIVERY) {
			title = "派件员";
		} else if (flag == ConstansValues.PROBLEM_SHIPMENT) {
			title = "问题类型";
		} else if (flag == ConstansValues.SIGN_FOR) {
			title = "签收人";
		}
		return title;
	}

	public void initView() {
		deliveryTitle = (TitleView) findViewById(R.id.delivery_title);
		deliveryTitle.setTitle(titleValue(flag));
		deliveryTitle.setRightTextGone(true);
		deliveryTitle.setBackgroundColor(getResources()
				.getColor(R.color.yellow));

		midTitle = (DrawableCenterTextView) findViewById(R.id.delivery_mid_title);
		midTitle.setText(setMidTitle(flag));

		save = (DrawableCenterTextView) findViewById(R.id.delivery_save);
		upload = (DrawableCenterTextView) findViewById(R.id.delivery_upload);
		save.setOnClickListener(this);
		upload.setOnClickListener(this);

		deliveryList = (ListView) findViewById(R.id.delivery_list);

		model = new ArrayList<ListDialogModel>();
		data = new ArrayList<ScanRecord>();
		ScanRecord record = new ScanRecord();
		record.setYear(year);
		record.setDate(dates);
		record.setTime(times);
		record.setIsUpload(UPLOAD_NOT);
		if (flag == ConstansValues.PROBLEM_SHIPMENT) {

			record.setExpressId(result);
			record.setExpressStatus("最新状态：");
			record.setScanType(String.valueOf(flag));
			Log.e("flags", String.valueOf(flag));
			record.setCourier(SharedPreferencesData.getStoData(this,
					"courierName"));
			data.add(record);
			type = sqlManager.queryProblemType();

			for (int i = 0, len = type.size(); i < len; i++) {
				ListDialogModel dialogModel = new ListDialogModel();
				dialogModel.setListContent(type.get(i).getProblemType());
				model.add(dialogModel);
			}

		} else if (flag == ConstansValues.SIGN_FOR) {
			record.setExpressId(result);
			record.setExpressStatus("最新状态：");
			record.setScanType(String.valueOf(flag));
			record.setCourier("选签收人");
			data.add(record);
			String[] arrs = getResources().getStringArray(R.array.qianshou);

			for (String string : arrs) {
				ListDialogModel dialogModel = new ListDialogModel();
				dialogModel.setListContent(string);
				model.add(dialogModel);
			}

		} else {
			record.setExpressId(result);
			record.setExpressStatus("最新状态：");
			record.setScanType(String.valueOf(flag));
			record.setCourier(SharedPreferencesData.getStoData(this,
					"courierName"));
			data.add(record);
			model = null;
		}

		adapter = new DeliveryAdapter(this, data, model, flag);
		deliveryList.setAdapter(adapter);

		deliveryList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (data.get(position).getIsChosen() == 0) {
					data.get(position).setIsChosen(1);
				} else {
					data.get(position).setIsChosen(0);
				}

				adapter.notifyDataSetChanged();
			}
		});

	}

	public String titleValue(int flag) {
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.delivery_save:
			int len = data.size();
			if (len == 0) {
				Toast.makeText(DeliveryActivity.this, "请扫描后再来提交",
						Toast.LENGTH_SHORT).show();
				return;
			}
			for (int i = 0; i < len; i++) {
				if (data.get(i).getIsChosen() == 1) {
					sqlManager.addScanRecord(data.get(i));
					Toast.makeText(DeliveryActivity.this, "保存成功",
							Toast.LENGTH_SHORT).show();
				}
			}
			break;

		case R.id.delivery_upload:
			int lens = data.size();
			if (lens == 0) {
				return;
			}else {
				for (int i = 0; i < lens; i++) {
					if (data.get(i).getIsUpload().equals("0")) {
						String transferParam = AESEncrypt
								.encrypt("{\"EnterpriseID\":\"TJSTO\","
										+ "\"StationID\":\"300000\","
										+ "\"PDAID\":\"869573010995351\","
										+ "\"Phone\":\"13700201234\","
										+ "\"EmpNo\":\"0001\","
										+ "\"Password\":\"1234\","
										+ "\"Version\":\"V1.0\","
										+ "\"DefaultLogic\":false,"
										+ "\"Compress\":true,\"FileType\":0}");
						
						String cotent = "<XML><ScanType Name=\"SJ\">" +
								"<ExpressType Name=\"C_N\">" +
								"<Row>049000006875" +
								"    20141101151128" +
								"468123400000   9000000000     " +
								"20141101" +
								"359836049340965" +
								"</Row>" +
								"</ExpressType>" +
								"</ScanType>" +
								"</XML>";
					}
				}
			}
			break;
		}
	}
}
