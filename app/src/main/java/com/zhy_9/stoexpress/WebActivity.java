package com.zhy_9.stoexpress;

import com.zhy_9.stoexpress.view.TitleView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebActivity extends BaseActivity {

	private TitleView title;
	private WebView help;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_web);

		initView();
	}

	@SuppressLint("SetJavaScriptEnabled")
	private void initView() {
		title = (TitleView) findViewById(R.id.web_title);
		title.setTitle("°ÍÇ¹É¨ÃèËµÃ÷");
		title.setBackgroundColor(getResources().getColor(R.color.yellow));
		title.setRightTextGone(true);

		help = (WebView) findViewById(R.id.help_web);
		help.getSettings().setJavaScriptEnabled(true);
		help.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				return false;
			}
		});
		help.loadUrl("http://m.kuaidihelp.com/help/baqiang_sto.html");

	}

}
