package com.zhy_9.stoexpress.adapter;

import java.util.List;

import com.zhy_9.stoexpress.R;
import com.zhy_9.stoexpress.model.RecordCounts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MonthCountAdapter extends BaseAdapter {

	Context context;
	LayoutInflater inflater;
	List<RecordCounts> data;
	
	
	
	public MonthCountAdapter(Context context, List<RecordCounts> data) {
		super();
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
		CountViewHolder holder = null;
		if (convertView == null) {
			holder = new CountViewHolder();
			convertView = inflater.inflate(R.layout.counts_month, null);
			holder.month = (TextView) convertView.findViewById(R.id.counts_record_month);
			holder.counts = (TextView) convertView.findViewById(R.id.counts_record_counts);
			convertView.setTag(holder);
		}else {
			holder = (CountViewHolder) convertView.getTag();
		}
		holder.month.setText(data.get(position).getMonth());
		holder.counts.setText(data.get(position).getCounts());
		
		return convertView;
	}

	class CountViewHolder{
		TextView month;
		TextView counts;
	}
	
}
