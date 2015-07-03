package com.zhy_9.stoexpress.view;

import java.util.List;

import com.zhy_9.stoexpress.R;
import com.zhy_9.stoexpress.adapter.DialogListAdapter;
import com.zhy_9.stoexpress.model.ListDialogModel;
import com.zhy_9.stoexpress.util.CommonUtil;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class ListDialog extends Dialog {

	private TextView title;
	private ListView list;
	private LinearLayout bottomLinear;
	private Button cancel, ensure;
	private Context context;
	private DialogListAdapter adapter;
	private List<ListDialogModel> data;
	private String diaTitle;

	private float currentY, tanslationY;

	private static int default_width = 280;
	private static int default_height = 360;
	private ListDialogCallBack callBack;
	private String content;

	public ListDialog(Context context, int style) {
		super(context, style);
		this.context = context;
	}

	public ListDialog(Context context, List<ListDialogModel> data,
			String diaTitle, ListDialogCallBack callBack) {
		this(context, R.style.list_dialog);
		this.data = data;
		this.diaTitle = diaTitle;
		this.callBack = callBack;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setBackgroundDrawable(new BitmapDrawable());
		setContentView(R.layout.list_dialog);
		title = (TextView) findViewById(R.id.list_dialog_title);
		list = (ListView) findViewById(R.id.list_dialog_list);
		bottomLinear = (LinearLayout) findViewById(R.id.list_dialog_bottom);
		cancel = (Button) findViewById(R.id.list_dialog_cancel);
		ensure = (Button) findViewById(R.id.list_dialog_ensure);
		adapter = new DialogListAdapter(context, data);
		tanslationY = currentY + 500;

		list.setAdapter(adapter);
		list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				data.get(position).setIsChosen(1);
				for (int i = 0, len = data.size(); i < len; i++) {
					if (i != position) {
						data.get(i).setIsChosen(0);
					}

				}
				adapter.notifyDataSetChanged();

			}
		});
		cancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				ListDialog.this.dismiss();
			}
		});
		ensure.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				for (int i = 0, len = data.size(); i < len; i++) {
					if (data.get(i).getIsChosen() == 1) {
						content = data.get(i).getListContent();
					}
				}
				if (callBack != null) {
					callBack.getListContent(content);
					ListDialog.this.dismiss();
				}

			}
		});

		Window window = getWindow();
		WindowManager.LayoutParams params = window.getAttributes();
		float density = CommonUtil.getDensity(context);
		params.width = (int) (default_width * density);
		params.height = (int) (default_height * density);
		params.gravity = Gravity.CENTER;
		window.setAttributes(params);
		setTitle(diaTitle);
	}

	public interface ListDialogCallBack {
		public void getListContent(String content);
	}

	public void setTitle(String diaTitle) {
		if (diaTitle != null) {
			title.setText(diaTitle);
		}
	}
}
