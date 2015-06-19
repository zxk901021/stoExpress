package com.zhy_9.stoexpress.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class TitleAdapter extends BaseAdapter {
	
	private Context mContext;
	private List<String> title;

	public TitleAdapter(Context mContext, List<String> list) {
		super();
		this.mContext = mContext;
		this.title = list;
	}

	@Override
	public int getCount() {
		return title.size();
	}

	@Override
	public Object getItem(int position) {
		return title.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView view = (TextView) convertView;
		if (view == null) {
			view = new TextView(mContext);

		}
		view.setText(title.get(position));
		view.setTextColor(Color.BLACK);
		view.setTextSize(25);
		return view;
	}

}
