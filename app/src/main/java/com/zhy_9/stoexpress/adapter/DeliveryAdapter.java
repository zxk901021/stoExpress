package com.zhy_9.stoexpress.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhy_9.stoexpress.R;
import com.zhy_9.stoexpress.model.ListDialogModel;
import com.zhy_9.stoexpress.model.ScanRecord;
import com.zhy_9.stoexpress.view.ListDialog;

import java.util.List;

public class DeliveryAdapter extends BaseAdapter {
	
	
	private List<ScanRecord> data;
	private LayoutInflater inflater;
	private Context context;
	private List<ListDialogModel> model;
	
	public DeliveryAdapter(Context context, List<ScanRecord> data, List<ListDialogModel> model) {
		this.data = data;
		this.context = context;
		this.model = model;
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
		DeliveryHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.delivery_list_item, null);
			holder = new DeliveryHolder();
			holder.isChosen = (ImageView) convertView.findViewById(R.id.delivery_chosen);
			holder.orderId = (TextView) convertView.findViewById(R.id.delivery_order_id);
			holder.status = (TextView) convertView.findViewById(R.id.delivery_order_status);
			holder.courier = (TextView) convertView.findViewById(R.id.delivery_order_courier);
			holder.delete = (ImageView) convertView.findViewById(R.id.delivery_delete);
			convertView.setTag(holder);
		}else {
			holder = (DeliveryHolder) convertView.getTag();
		}
		if (data.get(position).getIsChosen() == 1) {
			holder.isChosen.setImageResource(R.drawable.sto_batch_add_checked);
		}else {
			holder.isChosen.setImageResource(R.drawable.select_edit_identity);
		}
		if (data.get(position).getDelete() == 1) {
			data.remove(position);
			notifyDataSetChanged();
		}
		holder.orderId.setText(data.get(position).getExpressId());
		holder.status.setText(data.get(position).getExpressStatus());
		holder.courier.setText(data.get(position).getCourier());
		
		
		holder.courier.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ListDialog dialog = new ListDialog(context, model);
				dialog.show();
			}
		});
		
		return convertView;
	}

	class DeliveryHolder {
		ImageView isChosen;
		TextView orderId;
		TextView status;
		TextView courier;
		ImageView delete;
		
	}
	
}
