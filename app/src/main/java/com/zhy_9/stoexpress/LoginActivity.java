package com.zhy_9.stoexpress;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class LoginActivity extends Activity implements OnClickListener,
		OnTouchListener {

	private EditText phone;
	private EditText passwd;
	private Button login;
	private ImageView delPhone, delPasswd;
	boolean phoneTag = true, passTag = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		initView();
	}

	public void initView() {
		phone = (EditText) findViewById(R.id.login_phone);
		passwd = (EditText) findViewById(R.id.login_password);
		login = (Button) findViewById(R.id.login_ensure);
		delPhone = (ImageView) findViewById(R.id.delete_phone);
		delPasswd = (ImageView) findViewById(R.id.delete_passwd);

		delPhone.setOnClickListener(this);
		delPasswd.setOnClickListener(this);

		phone.setOnTouchListener(this);
		passwd.setOnTouchListener(this);
		addTextChangedListener();

	}

	public void addTextChangedListener() {
		phone.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (s != null) {
					if (s.length() > 0) {
						delPhone.setVisibility(View.VISIBLE);
						phone.setCursorVisible(true);

					} else {
						delPhone.setVisibility(View.GONE);
					}
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});

		passwd.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (s != null) {
					if (s.length() > 0) {
						delPasswd.setVisibility(View.VISIBLE);
					} else {
						delPasswd.setVisibility(View.GONE);
					}
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {

			}

			@Override
			public void afterTextChanged(Editable s) {

			}
		});

		login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LoginActivity.this,
						MainActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.delete_phone:
			phone.setText("");
			break;

		case R.id.delete_passwd:
			passwd.setText("");
			break;
		}

	}

	@SuppressWarnings("deprecation")
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			switch (v.getId()) {
			case R.id.login_phone:
				if (phoneTag) {
					phone.requestFocus();
					Drawable drawable = getResources().getDrawable(
							R.drawable.icon_mobile_hover);
					drawable.setBounds(0, 0, drawable.getMinimumWidth(),
							drawable.getMinimumHeight());
					phone.setCompoundDrawables(drawable, null, null, null);
					phoneTag = false;
				}
				if (passTag == false) {
					Drawable drawable = getResources().getDrawable(
							R.drawable.icon_pwd);
					drawable.setBounds(0, 0, drawable.getMinimumWidth(),
							drawable.getMinimumHeight());
					passwd.setCompoundDrawables(drawable, null, null, null);
					passTag = true;
				}
				break;

			case R.id.login_password:
				if (passTag) {
					passwd.requestFocus();
					Drawable drawable = getResources().getDrawable(
							R.drawable.icon_pwd_hover);
					drawable.setBounds(0, 0, drawable.getMinimumWidth(),
							drawable.getMinimumHeight());
					passwd.setCompoundDrawables(drawable, null, null, null);
					passTag = false;
				}
				if (phoneTag == false) {
					Drawable drawable = getResources().getDrawable(
							R.drawable.icon_mobile);
					drawable.setBounds(0, 0, drawable.getMinimumWidth(),
							drawable.getMinimumHeight());
					phone.setCompoundDrawables(drawable, null, null, null);
					phoneTag = true;
				}
				break;
			}
			break;

		case MotionEvent.ACTION_UP:
			v.performClick();
			break;

		default:
			break;
		}
		return false;
	}

}
