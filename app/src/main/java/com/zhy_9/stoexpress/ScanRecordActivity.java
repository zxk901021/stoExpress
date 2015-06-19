package com.zhy_9.stoexpress;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.zhy_9.stoexpress.adapter.TitleAdapter;
import com.zhy_9.stoexpress.fragment.ListFragment;
import com.zhy_9.stoexpress.view.TitleView;
import com.zhy_9.stoexpress.view.TitleView.RightBtnCallBack;

import java.util.List;

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
			this.setTitle(getResources().getString(R.string.paijianshaomian));
			this.setBackgroundColor(getResources().getColor(R.color.yellow));
			this.setRightText(getResources().getString(R.string.qiaoshoushaomiao));
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
			this.setTitle(getResources().getString(R.string.saomiaotongji));
			this.setBackgroundColor(getResources().getColor(R.color.yellow));
			this.setRightTextGone(true);
		}
		
	}


	@Override
	protected int supplyTabs(List<TabInfo> tabs) {
		tabs.add(new TabInfo(0, getResources().getString(R.string.shoujian), ListFragment.class));
		tabs.add(new TabInfo(1, getResources().getString(R.string.fajian), ListFragment.class));
		tabs.add(new TabInfo(2, getResources().getString(R.string.paijian), ListFragment.class));
		tabs.add(new TabInfo(3, getResources().getString(R.string.daojian), ListFragment.class));
		tabs.add(new TabInfo(4, getResources().getString(R.string.qianshoujian), ListFragment.class));
		
		return 0;
	}
	
	
	
	
}
