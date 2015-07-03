package com.zhy_9.stoexpress.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;

import com.zhy_9.stoexpress.R;
import com.zhy_9.stoexpress.adapter.ScanRecordAdapter;
import com.zhy_9.stoexpress.db.SQLManager;
import com.zhy_9.stoexpress.model.ConstansValues;
import com.zhy_9.stoexpress.model.Record;
import com.zhy_9.stoexpress.util.AESEncrypt;
import com.zhy_9.stoexpress.util.GZipUtil;
import com.zhy_9.stoexpress.util.WCFClient;
import com.zhy_9.stoexpress.view.DrawableCenterTextView;

import java.util.ArrayList;
import java.util.List;

public class DeliveryListFragment extends Fragment {

	private ListView listView;
	private ScanRecordAdapter adapter;
	private List<Record> data;
	private DrawableCenterTextView upload;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View layout = inflater.inflate(R.layout.list_fragment, null);
		return layout;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		Intent intent = getActivity().getIntent();
		int flag = intent.getIntExtra(ConstansValues.BUTTON_FLAG, 0);

		listView = (ListView) getActivity().findViewById(
				R.id.list_fragment_list);
		upload = (DrawableCenterTextView) getActivity().findViewById(
				R.id.list_upload_delivery);
		data = new ArrayList<Record>();
		SQLManager manager = new SQLManager(getActivity());
		if (flag == ConstansValues.SCAN_RECORD) {
			data = manager.queryScanRecord(ConstansValues.DELIVERY_DB_TYPE);
		} else {
			data = manager.queryScanRecordUnUpload(ConstansValues.DELIVERY_DB_TYPE);
			upload.setVisibility(View.VISIBLE);
			upload.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					new Thread(new Runnable() {
						
						@Override
						public void run() {
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
							String cotent = "<XML><ScanType Name=\"PJ\">" +
									"<ExpressType Name=\"C_N\">" +
									"<Row>" +
									"04" +
									"9000006875    " +
									"20141101151128" +
									"468123400000    " +
									"9000000000     " +
									"20141101" +
									"359836049340965" +
									"</Row>" +
									"</ExpressType>" +
									"</ScanType>" +
									"</XML>";
							String content = GZipUtil.gZipString(cotent);
							String result = WCFClient.upload(transferParam, content);
							Log.e("result", result);
						}
					}).start();
					
				}
			});
		}

		adapter = new ScanRecordAdapter(getActivity(), data);
		listView.setAdapter(adapter);
	}

}
