package com.zhy_9.stoexpress.adapter;

import java.util.List;

import com.zhy_9.stoexpress.R;
import com.zhy_9.stoexpress.model.ConstansValues;
import com.zhy_9.stoexpress.model.ListDialogModel;
import com.zhy_9.stoexpress.model.ScanRecord;
import com.zhy_9.stoexpress.view.CircleCornerDialog;
import com.zhy_9.stoexpress.view.ListDialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class DeliveryAdapter extends BaseAdapter {

	private List<ScanRecord> data;
	private LayoutInflater inflater;
	private Context context;
	private List<ListDialogModel> model;
	private int flag;

	public DeliveryAdapter(Context context, List<ScanRecord> data,
			List<ListDialogModel> model, int flag) {
		this.data = data;
		this.context = context;
		this.model = model;
		this.flag = flag;
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		final DeliveryHolder holder;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.delivery_list_item, null);
			holder = new DeliveryHolder();
			holder.isChosen = (ImageView) convertView
					.findViewById(R.id.delivery_chosen);
			holder.orderId = (TextView) convertView
					.findViewById(R.id.delivery_order_id);
			holder.status = (TextView) convertView
					.findViewById(R.id.delivery_order_status);
			holder.courier = (TextView) convertView
					.findViewById(R.id.delivery_order_courier);
			holder.delete = (ImageView) convertView
					.findViewById(R.id.delivery_delete);
			convertView.setTag(holder);
		} else {
			holder = (DeliveryHolder) convertView.getTag();
		}
		if (data.get(position).getIsChosen() == 1) {
			holder.isChosen.setImageResource(R.drawable.sto_batch_add_checked);
		} else {
			holder.isChosen.setImageResource(R.drawable.select_edit_identity);
		}
		if (data.get(position).getDelete() == 1) {
			data.remove(position);
			notifyDataSetChanged();
		}
		holder.orderId.setText(data.get(position).getExpressId());
		holder.status.setText(data.get(position).getExpressStatus());
		holder.courier.setText(data.get(position).getCourier());

		holder.delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				data.remove(position);
				notifyDataSetChanged();
			}
		});

		holder.courier.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (flag == ConstansValues.DELIVERY) {
					View dialogView = inflater.inflate(R.layout.batch_scan_dialog_single_line, null);
					CircleCornerDialog dialog = new CircleCornerDialog(
							context, 300, 256, dialogView,
							R.style.batch_dialog);
					dialog.show();
				}else {
					String dialogTitle = "";
					if (flag == ConstansValues.PROBLEM_SHIPMENT) {
						dialogTitle = "选择问题类型";
					}else if (flag == ConstansValues.SIGN_FOR) {
						dialogTitle = "选签收人";
					}
					ListDialog dialog = new ListDialog(context, model, dialogTitle,
							new ListDialog.ListDialogCallBack() {

								@Override
								public void getListContent(String content) {
									holder.courier.setText(content);
									data.get(position).setCourier(content);
									notifyDataSetChanged();
								}
							});
					dialog.show();
				}
				
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
