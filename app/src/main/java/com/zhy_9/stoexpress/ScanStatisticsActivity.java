package com.zhy_9.stoexpress;

import java.util.List;

import com.zhy_9.stoexpress.fragment.DeliveryStatisticsFragment;
import com.zhy_9.stoexpress.fragment.ProblemStatisticsFragment;
import com.zhy_9.stoexpress.fragment.SignForStatisticsFragment;

import android.os.Bundle;

public class ScanStatisticsActivity extends IndicatorFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.setTitle(getResources().getString(R.string.scan_count));
		this.setBackgroundColor(getResources().getColor(R.color.yellow));
		this.setRightTextGone(true);
	}

	@Override
	protected int supplyTabs(List<TabInfo> tabs) {
		tabs.add(new TabInfo(0, getResources().getString(R.string.paijian),
				DeliveryStatisticsFragment.class));
		tabs.add(new TabInfo(1, getResources().getString(R.string.wentijian),
				ProblemStatisticsFragment.class));
		tabs.add(new TabInfo(2,
				getResources().getString(R.string.qianshoujian),
				SignForStatisticsFragment.class));
		return 0;
	}

}
