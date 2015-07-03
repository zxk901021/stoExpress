package com.zhy_9.stoexpress.adapter;

import java.util.List;

import com.zhy_9.stoexpress.R;
import com.zhy_9.stoexpress.model.ListDialogModel;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DialogListAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private List<ListDialogModel> data;

	public DialogListAdapter(Context context, List<ListDialogModel> data) {
		inflater = LayoutInflater.from(context);
		this.data = data;
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		DialogViewHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.dialog_list_item, null);
			holder = new DialogViewHolder();
			holder.select = (ImageView) convertView
					.findViewById(R.id.dialog_item_select);
			holder.itemText = (TextView) convertView
					.findViewById(R.id.dialog_item_text);
			holder.delete = (ImageView) convertView
					.findViewById(R.id.dialog_item_delete);
			convertView.setTag(holder);
		} else {
			holder = (DialogViewHolder) convertView.getTag();
		}
		holder.itemText.setText(data.get(position).getListContent());
		holder.itemText.setTextColor(Color.BLACK);
		if (data.get(position).getIsChosen() == 1) {

			holder.select.setImageResource(R.drawable.sto_batch_add_checked);
		} else {
			holder.select.setImageResource(R.drawable.select_edit_identity);
		}
		if (data.get(position).getIsVisiable() == 0) {
			holder.delete.setVisibility(View.GONE);
		} else {
			holder.delete.setVisibility(View.VISIBLE);
			holder.delete.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					data.remove(position);
					notifyDataSetChanged();
				}
			});
		}

		return convertView;
	}

	class DialogViewHolder {
		private ImageView select;
		private TextView itemText;
		private ImageView delete;
	}

}
