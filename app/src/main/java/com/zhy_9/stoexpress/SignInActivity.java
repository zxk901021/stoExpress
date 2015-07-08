package com.zhy_9.stoexpress;


import com.zhy_9.stoexpress.model.StoInfo;
import com.zhy_9.stoexpress.util.CommonUtil;
import com.zhy_9.stoexpress.util.WCFClient;
import com.zhy_9.stoexpress.view.TitleView;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;

public class SignInActivity extends Activity implements OnTouchListener,
		OnClickListener {

	private TitleView title;
	private EditText phoneNumber, staffNumber, branchNumber, passwordSet,
			passwordEnsure;
	private boolean[] tags = new boolean[5];

	private Button next;

	private String phoneStr, staffStr, branchStr, passwordSetStr,
			passwordEnsureStr, registerInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_in);

		initView();
	}

	private void initView() {
		title = (TitleView) findViewById(R.id.sign_in_title);
		title.setBackgroundColor(getResources().getColor(R.color.green));
		title.setTitle("注册");
		title.setRightTextGone(true);
		for (int i = 0; i < tags.length; i++) {
			tags[i] = true;
		}

		phoneNumber = (EditText) findViewById(R.id.sign_in_phone);
		staffNumber = (EditText) findViewById(R.id.sign_in_staff_number);
		branchNumber = (EditText) findViewById(R.id.branch_number);
		passwordSet = (EditText) findViewById(R.id.password_set);
		passwordEnsure = (EditText) findViewById(R.id.password_ensure);

		next = (Button) findViewById(R.id.sign_in_next);

		next.setOnClickListener(this);

	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			switch (v.getId()) {
			case R.id.sign_in_phone:
				if (tags[0]) {
					phoneNumber.requestFocus();
					Drawable drawable = getResources().getDrawable(
							R.drawable.sign_in_mobile_hover);
					drawable.setBounds(0, 0, drawable.getMinimumWidth(),
							drawable.getMinimumHeight());
					phoneNumber
							.setCompoundDrawables(drawable, null, null, null);
					tags[0] = false;
				}
				if (tags[1] == false) {
					Drawable drawable = getResources().getDrawable(
							R.drawable.sign_in_verify_hover);
					drawable.setBounds(0, 0, drawable.getMinimumWidth(),
							drawable.getMinimumHeight());
					staffNumber
							.setCompoundDrawables(drawable, null, null, null);
					tags[1] = true;
				}
				if (tags[2] == false) {

				}
				break;

			case R.id.sign_in_staff_number:

				break;

			case R.id.branch_number:

				break;

			case R.id.password_set:

				break;

			case R.id.password_ensure:

				break;
			}
			break;

		case MotionEvent.ACTION_UP:
			v.performClick();
			break;
		}
		return false;
	}

	@SuppressWarnings("deprecation")
	public void setDrawable(Drawable drawable, EditText editText, int drawId) {
		drawable = getResources().getDrawable(drawId);
		drawable.setBounds(0, 0, drawable.getMinimumWidth(),
				drawable.getMinimumHeight());
		editText.setCompoundDrawables(drawable, null, null, null);
	}

	public void setDrawableLeft() {
		Drawable drawable = null;
		if (tags[0]) {
			phoneNumber.requestFocus();
			setDrawable(drawable, phoneNumber, R.drawable.sign_in_mobile_hover);
			tags[0] = false;
		} else {
			setDrawable(drawable, phoneNumber, R.drawable.sign_in_mobile);
		}
		if (tags[1]) {
			setDrawable(drawable, staffNumber, R.drawable.sign_in_verify_hover);
			tags[1] = false;
		} else {
			setDrawable(drawable, staffNumber, R.drawable.sign_in_verify);
		}
		if (tags[2]) {
			setDrawable(drawable, branchNumber, R.drawable.sign_in_verify_hover);
			tags[2] = false;
		} else {
			setDrawable(drawable, branchNumber, R.drawable.sign_in_verify);
		}
		if (tags[3]) {
			setDrawable(drawable, passwordSet, R.drawable.sign_in_pwd_hover);
			tags[3] = false;
		} else {
			setDrawable(drawable, passwordSet, R.drawable.sign_in_pwd);
		}
		if (tags[4]) {
			setDrawable(drawable, passwordEnsure, R.drawable.sign_in_pwd_hover);
			tags[4] = false;
		} else {
			setDrawable(drawable, passwordEnsure, R.drawable.sign_in_pwd);
		}
	}

	public void getEditContent() {
		phoneStr = phoneNumber.getText().toString();
		if (TextUtils.isEmpty(phoneStr) || phoneStr.length() != 11) {
			CommonUtil.showToast(this, "您输入的手机号有误！");
			return;
		}
		staffStr = staffNumber.getText().toString();
		if (TextUtils.isEmpty(staffStr)) {
			CommonUtil.showToast(this, "您输入的员工编号有误！");
			return;
		}
		branchStr = branchNumber.getText().toString();
		if (TextUtils.isEmpty(branchStr) || branchStr.length() != 6) {
			CommonUtil.showToast(this, "您输入的网点编号有误！");
			return;
		}
		passwordSetStr = passwordSet.getText().toString();
		passwordEnsureStr = passwordEnsure.getText().toString();
		if (TextUtils.isEmpty(passwordSetStr)
				|| TextUtils.isEmpty(passwordEnsureStr)) {
			CommonUtil.showToast(this, "密码输入有误！");
			return;
		}
		if (!TextUtils.equals(passwordSetStr, passwordEnsureStr)) {
			CommonUtil.showToast(this, "两次密码输入不一致");
			return;
		} else {
			if (passwordSetStr.replace(" ", "").length() != 6) {
				CommonUtil.showToast(this, "密码为6位数字");
				return;
			}
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.sign_in_next:
			StoInfo info = new StoInfo();
			getEditContent();
			info.setCourierPhone(phoneStr);
			info.setBranchNumber(branchStr);
			info.setStaffNumber(staffStr);
			info.setPassword(passwordSetStr);
			registerInfo = CommonUtil.setTransferParam(info,
					SignInActivity.this);
			Log.e("registerInfo", registerInfo);
			new Thread(new Runnable() {

				@Override
				public void run() {
					String result = WCFClient.register(registerInfo);
					Log.e("resuklt", result);
				}
			}).start();

			break;

		default:
			break;
		}
	}

}
