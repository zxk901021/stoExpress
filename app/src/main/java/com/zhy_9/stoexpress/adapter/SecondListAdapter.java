package com.zhy_9.stoexpress.adapter;

import java.util.List;

import com.zhy_9.stoexpress.R;
import com.zhy_9.stoexpress.model.ScanRecord;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SecondListAdapter extends BaseAdapter {

	List<ScanRecord> data;
	LayoutInflater inflater;

	public SecondListAdapter(List<ScanRecord> data, Context context) {
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
		SecondListHolder holder;
		if (convertView == null) {
			holder = new SecondListHolder();
			convertView = inflater.inflate(R.layout.second_list_layout, null);
			holder.expressId = (TextView) convertView
					.findViewById(R.id.express_id);
			holder.expressTime = (TextView) convertView
					.findViewById(R.id.express_time);
			holder.courier = (TextView) convertView.findViewById(R.id.courier);
			convertView.setTag(holder);
		} else {
			holder = (SecondListHolder) convertView.getTag();
		}
		holder.expressTime.setText(data.get(position).getTime());
		holder.expressId.setText(data.get(position).getExpressId());
		holder.courier.setText(data.get(position).getCourier());
		return convertView;
	}

	class SecondListHolder {
		TextView expressId;
		TextView expressTime;
		TextView courier;
	}

}
