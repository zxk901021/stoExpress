package com.zhy_9.stoexpress.fragment;

import com.zhy_9.stoexpress.R;
import com.zhy_9.stoexpress.adapter.ScanRecordAdapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


public class ListFragment extends Fragment {
	
	private ListView listView;
	private ScanRecordAdapter adapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View layout = inflater.inflate(R.layout.list_fragment, null);
		return layout;
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		listView = (ListView) getActivity().findViewById(R.id.list_fragment_list);
	}
	
}
