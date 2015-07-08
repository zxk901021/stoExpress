package com.zhy_9.stoexpress.fragment;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import com.zhy_9.stoexpress.R;
import com.zhy_9.stoexpress.adapter.ScanRecordAdapter;
import com.zhy_9.stoexpress.db.SQLManager;
import com.zhy_9.stoexpress.model.ConstansValues;
import com.zhy_9.stoexpress.model.Record;
import com.zhy_9.stoexpress.util.AESEncrypt;
import com.zhy_9.stoexpress.util.GZipUtil;
import com.zhy_9.stoexpress.util.WCFClient;
import com.zhy_9.stoexpress.view.DrawableCenterTextView;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ListView;

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
											+ "\"Phone\":\"18920680203\","
											+ "\"EmpNo\":\"0001\","
											+ "\"Password\":\"7110\","
											+ "\"Version\":\"V1.0\","
											+ "\"DefaultLogic\":false,"
											+ "\"Compress\":true,\"FileType\":0}");
//							String cotent = "<XML><ScanType Name=\"PJ\">" +
//									"<ExpressType Name=\"C_N\">" +
//									"<Row>" +
//									"04" +
//									"9000006875    " +
//									"20141101151128" +
//									"468123400000    " +
//									"9000000000     " +
//									"20141101" +
//									"359836049340965" +
//									"</Row>" +
//									"</ExpressType>" +
//									"</ScanType>" +
//									"</XML>";
							String tongshi = "同事签收  ";
							String gbk = null;
							try {
								gbk = URLEncoder.encode(tongshi, "GBK");
							} catch (UnsupportedEncodingException e1) {
								e1.printStackTrace();
							}
							String signForContent = "<XML><ScanType Name=\"QS\">" +
									"<ExpressType Name=\"C_N\">" +
									"<Row>" +
									"99" +
									"3000000001    " +
									"20150708140228" +
									" " + " " +
									"468123400000    " +
									"3000000001    " +
									"20150708" +
									" " + 
									"同事签收  " + 
									"        " +
									"          " + 
									"              " +
									"        " + 
									"               " +
									"359836049340965" +
									"</Row>" +
									"</ExpressType>" +
									"</ScanType>" +
									"</XML>";
//							StringBuilder builder = new StringBuilder();
//							builder.append("99").
//							append("3000000001    ").
//							append("20150708140228").
//							append(" ").
//							append(" ").
//							append("468123400000    ").
//							append("3000000001    ").
//							append("20150708").
//							append(" ").
//							append(gbk).
//							append("        ").
//							append("          ").
//							append("              ").
//							append("        ").
//							append("               ").
//							append("359836049340965");
//							String content = GZipUtil.gZipString(cotent);
							try {
								String params = URLEncoder.encode(signForContent, "GBK");
//								Log.e("gbk", signForContent.length() + "");
								String param = GZipUtil.gZipString(signForContent);
//								Log.e("gzip", param);
								String result = WCFClient.upload(transferParam, param);
								Log.e("up", transferParam);
								Log.e("up", param);
//								Log.e("result", result);
							} catch (UnsupportedEncodingException e) {
								e.printStackTrace();
							}
							
							
						}
					}).start();
					
				}
			});
		}

		adapter = new ScanRecordAdapter(getActivity(), data);
		listView.setAdapter(adapter);
	}

}
