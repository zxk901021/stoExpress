package com.zhy_9.stoexpress.view;

import java.util.List;

import com.zhy_9.stoexpress.R;
import com.zhy_9.stoexpress.adapter.DialogListAdapter;
import com.zhy_9.stoexpress.model.ListDialogModel;
import com.zhy_9.stoexpress.util.CommonUtil;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
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

	private float currentY, tanslationY;

	private static int default_width = 280;
	private static int default_height = 360;

	public ListDialog(Context context, int style) {
		super(context, style);
		this.context = context;
	}

	public ListDialog(Context context, List<ListDialogModel> data) {
		this(context, R.style.list_dialog);
		this.data = data;

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
		
		
		
//		list.setOnScrollListener(new OnScrollListener() {
//
//			@Override
//			public void onScrollStateChanged(AbsListView view, int scrollState) {
//
//				switch (scrollState) {
//				case SCROLL_STATE_FLING:
//					Log.e("scroll", "SCROLL_STATE_FLING");
//					break;
//
//				case SCROLL_STATE_IDLE:
//					Log.e("scroll", "SCROLL_STATE_IDLE");
//					break;
//
//				case SCROLL_STATE_TOUCH_SCROLL:
//					Log.e("scroll", "SCROLL_STATE_TOUCH_SCROLL");
//					if (bottomY == currentY) {
//						ObjectAnimator animator = ObjectAnimator.ofFloat(
//								takePhoto, "translationY", currentY,
//								tanslationY);
//						animator.setDuration(500);
//						animator.start();
//					} else {
//						ObjectAnimator animator = ObjectAnimator.ofFloat(
//								takePhoto, "translationY", tanslationY,
//								currentY);
//						animator.setDuration(500);
//						animator.start();
//					}
//					break;
//				}
//			}
//
//			@Override
//			public void onScroll(AbsListView view, int firstVisibleItem,
//					int visibleItemCount, int totalItemCount) {
//
//			}
//		});

		Window window = getWindow();
		WindowManager.LayoutParams params = window.getAttributes();
		float density = CommonUtil.getDensity(context);
		params.width = (int) (default_width * density);
		params.height = (int) (default_height * density);
		params.gravity = Gravity.CENTER;
		window.setAttributes(params);

	}

	public void setTitle(String diaTitle) {
		title.setText(diaTitle);
	}
}
