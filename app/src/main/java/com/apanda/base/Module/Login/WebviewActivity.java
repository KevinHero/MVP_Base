package com.apanda.base.Module.Login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Message;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.apanda.base.R;
import com.apanda.base.base.BaseActivity;

import butterknife.Bind;


public class WebviewActivity extends BaseActivity {

    @Bind(R.id.ib_back)
    ImageButton _IbBack;
    @Bind(R.id.textView)
    TextView _TextView;
    @Bind(R.id.rl_title)
    RelativeLayout _RlTitle;
    @Bind(R.id.webview_up)
    WebView _WebviewUp;
    private String _linkUrl;


    public static void startActivity(Context context, String title, String link_url) {
        Intent mIntent = new Intent(context, WebviewActivity.class);
        mIntent.putExtra("title", title);
        mIntent.putExtra("link_url", link_url);
        context.startActivity(mIntent);
    }


    @Override
    protected void refresh(Message msg) {

    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_layout_user_protocol;
    }

    @Override
    public void initPresenter() {

    }

    @Override
    public void initView() {
        Intent mIntent = getIntent();
        if (null != mIntent) {
            _WebviewUp.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
            _linkUrl = mIntent.getStringExtra("link_url");
            _WebviewUp.loadUrl(_linkUrl);
            _WebviewUp.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {

                    view.loadUrl(url);
                    return super.shouldOverrideUrlLoading(view, url);
                }


                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    if (url.equals(_linkUrl))
                        startProgressDialog();
                    super.onPageStarted(view, url, favicon);
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    stopProgressDialog();
                    super.onPageFinished(view, url);
                }
            });
            _TextView.setText(mIntent.getStringExtra("title"));
            _WebviewUp.getSettings().setJavaScriptEnabled(true);
        }
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}
