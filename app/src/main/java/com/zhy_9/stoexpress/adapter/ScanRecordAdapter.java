package com.zhy_9.stoexpress.adapter;

import java.util.List;

import com.zhy_9.stoexpress.R;
import com.zhy_9.stoexpress.model.Record;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ScanRecordAdapter extends BaseAdapter {

	Context context;
	LayoutInflater inflater;
	List<Record> data;

	public ScanRecordAdapter(Context context, List<Record> data) {
		this.context = context;
		this.data = data;
		inflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ScanRecordHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.list_expand_list, null);
			holder = new ScanRecordHolder();
			holder.textView = (TextView) convertView
					.findViewById(R.id.top_text);
			holder.listView = (ListView) convertView
					.findViewById(R.id.second_list);
			convertView.setTag(holder);
		} else {
			holder = (ScanRecordHolder) convertView.getTag();
		}
		holder.textView.setText(data.get(position).getDate());
		holder.listView.setAdapter(new SecondListAdapter(data.get(position)
				.getInfo(), context));
		setListViewHeightBasedOnChildren(holder.listView);

		return convertView;
	}

	public void setListViewHeightBasedOnChildren(ListView view) {
		ListAdapter adapter = view.getAdapter();
		if (adapter == null) {
			return;
		}
		int totalHeight = 0;
		for (int i = 0, len = adapter.getCount(); i < len; i++) {
			View listItem = adapter.getView(i, null, view);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}
		ViewGroup.LayoutParams params = view.getLayoutParams();
		params.height = totalHeight
				+ (view.getDividerHeight() * (adapter.getCount() - 1));
		view.setLayoutParams(params);
	}

	class ScanRecordHolder {
		TextView textView;
		ListView listView;
	}

}
