package com.zhy_9.stoexpress.view;

import com.zhy_9.stoexpress.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

public class ContentText extends FrameLayout{
	

	private TextView textKey;
	
	private TextView textValue;
	
	public ContentText(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.text_content, this);
		textKey = (TextView) findViewById(R.id.text_key);
		textValue = (TextView) findViewById(R.id.text_value);
	}
	
	public void setKeyText(String keyStr){
		textKey.setText(keyStr);
	}
	
	public void setValueText (String valueStr){
		textValue.setText(valueStr);
	}
	
	public void setTextColor(int color) {
	}
}
