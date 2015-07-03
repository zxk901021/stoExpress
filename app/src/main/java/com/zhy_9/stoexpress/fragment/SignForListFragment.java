package com.zhy_9.stoexpress.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import com.zhy_9.stoexpress.view.DrawableCenterTextView;

public class SignForListFragment extends Fragment {
	private ListView listView;
	private ScanRecordAdapter adapter;
	private List<Record> data;
	private DrawableCenterTextView upload;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View layout = inflater.inflate(R.layout.sign_for_fragment, null);
		return layout;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		Intent intent = getActivity().getIntent();
		int flag = intent.getIntExtra(ConstansValues.BUTTON_FLAG, 0);

		listView = (ListView) getActivity().findViewById(
				R.id.sign_for_fragment_list);
		upload = (DrawableCenterTextView) getActivity().findViewById(R.id.list_upload_sign_for);
		data = new ArrayList<Record>();
		SQLManager manager = new SQLManager(getActivity());
		if (flag == ConstansValues.SCAN_RECORD) {
			data = manager.queryScanRecord(ConstansValues.SIGN_FOR_DB_TYPE);
		} else {
			data = manager.queryScanRecordUnUpload(ConstansValues.SIGN_FOR_DB_TYPE);
			upload.setVisibility(View.VISIBLE);
			upload.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {

				}
			});
		}
		adapter = new ScanRecordAdapter(getActivity(), data);
		listView.setAdapter(adapter);
	}
}
