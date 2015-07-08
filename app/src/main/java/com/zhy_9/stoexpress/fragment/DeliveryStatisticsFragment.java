package com.zhy_9.stoexpress.fragment;

import java.util.ArrayList;
import java.util.List;

import com.zhy_9.stoexpress.R;
import com.zhy_9.stoexpress.adapter.CountRecordAdapter;
import com.zhy_9.stoexpress.db.SQLManager;
import com.zhy_9.stoexpress.model.CountRecords;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class DeliveryStatisticsFragment extends Fragment{
	
	private ListView listView;
	private CountRecordAdapter adapter;
	private List<CountRecords> data;
	private SQLManager manager;
	
	@Override
	@Nullable
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View layout = inflater.inflate(R.layout.delivery_statis, null);
		return layout;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		listView = (ListView) getActivity().findViewById(R.id.delivery_static_fragment);
		data = new ArrayList<CountRecords>();
		manager = new SQLManager(getActivity());
		data = manager.queryRecord("1");
		adapter = new CountRecordAdapter(getActivity(), data);
		listView.setAdapter(adapter);
	}
	
}
