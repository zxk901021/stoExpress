package com.zhy_9.stoexpress;

import android.content.Intent;
import android.os.Bundle;

import com.zhy_9.stoexpress.fragment.DeliveryListFragment;
import com.zhy_9.stoexpress.fragment.ProblemListFragment;
import com.zhy_9.stoexpress.fragment.SignForListFragment;
import com.zhy_9.stoexpress.model.ConstansValues;
import com.zhy_9.stoexpress.view.TitleView.RightBtnCallBack;

import java.util.List;

public class ScanRecordActivity extends IndicatorFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent = getIntent();
		int flag = intent.getIntExtra(ConstansValues.BUTTON_FLAG, 0);
		if (flag == ConstansValues.SCAN_RECORD) {
			this.setTitle(getResources().getString(R.string.scan_record));
			this.setBackgroundColor(getResources().getColor(R.color.yellow));
			this.setRightText(getResources().getString(R.string.saomiaotongji));
			this.setRightTextGone(false);
			this.setRightTextDrawable(R.drawable.san_count_default);
			this.setRightTextListener(new RightBtnCallBack() {

				@Override
				public void onRightBtnClick() {
					Intent intent = new Intent(ScanRecordActivity.this,
							ScanStatisticsActivity.class);
					startActivity(intent);
				}
			});
		} else {
			this.setTitle(getResources().getString(R.string.saomiaotongji));
			this.setBackgroundColor(getResources().getColor(R.color.yellow));
			this.setRightTextGone(true);
		}

	}

	@Override
	protected int supplyTabs(List<TabInfo> tabs) {
		tabs.add(new TabInfo(0, getResources().getString(R.string.paijian),
				DeliveryListFragment.class));
		tabs.add(new TabInfo(1, getResources().getString(R.string.wentijian),
				ProblemListFragment.class));
		tabs.add(new TabInfo(2,
				getResources().getString(R.string.qianshoujian),
				SignForListFragment.class));

		return 0;
	}

}
