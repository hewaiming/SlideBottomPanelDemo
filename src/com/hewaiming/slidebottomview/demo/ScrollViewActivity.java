package com.hewaiming.slidebottomview.demo;

import com.hewaiming.slidebottompanel.SlideBottomPanel;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class ScrollViewActivity extends AppCompatActivity {

    private String webUrl = "http://www.zhihu.com/question/29416073/answer/44340933";
    private WebView webView;
    private SlideBottomPanel sbv;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll_view);

        sbv = (SlideBottomPanel) findViewById(R.id.sbv);
        webView = (WebView) findViewById(R.id.wv_main);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(webUrl);
        webView.setWebChromeClient(new WebChromeClient());
    }

    @Override
    public void onBackPressed() {
        if (sbv.isPanelShowing()) {
            sbv.hide();
            return;
        }
        super.onBackPressed();
    }
}
