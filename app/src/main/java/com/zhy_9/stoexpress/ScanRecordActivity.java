package com.zhy_9.stoexpress;

import java.util.List;

import com.zhy_9.stoexpress.adapter.TitleAdapter;
import com.zhy_9.stoexpress.fragment.ListFragment;
import com.zhy_9.stoexpress.view.TitleView;
import com.zhy_9.stoexpress.view.TitleView.RightBtnCallBack;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class ScanRecordActivity extends IndicatorFragmentActivity {

	private List<String> titleSet;
	private TitleAdapter adapter;
	private TitleView titleView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		int flag = intent.getIntExtra("tag", 0);
		Log.e("flag", flag + "");
		if (flag == 0) {
			this.setTitle("扫描记录");
			this.setBackgroundColor(getResources().getColor(R.color.yellow));
			this.setRightText("扫描统计");
			this.setRightTextGone(false);
			this.setRightTextListener(new RightBtnCallBack() {
				
				@Override
				public void onRightBtnClick() {
					Intent intent = new Intent(ScanRecordActivity.this, ScanRecordActivity.class);
					intent.putExtra("tag", 1);
					startActivity(intent);
				}
			});
		}else {
			this.setTitle("扫描统计");
			this.setBackgroundColor(getResources().getColor(R.color.yellow));
			this.setRightTextGone(true);
		}
		
	}


	@Override
	protected int supplyTabs(List<TabInfo> tabs) {
		tabs.add(new TabInfo(0, "收件", ListFragment.class));
		tabs.add(new TabInfo(1, "发件", ListFragment.class));
		tabs.add(new TabInfo(2, "派件", ListFragment.class));
		tabs.add(new TabInfo(3, "到件", ListFragment.class));
		tabs.add(new TabInfo(4, "签收件", ListFragment.class));
		
		return 0;
	}
	
	
	
	
}
