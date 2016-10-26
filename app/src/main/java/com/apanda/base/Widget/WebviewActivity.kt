package com.apanda.base.Widget

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Message
import android.support.v7.widget.Toolbar
import android.view.KeyEvent
import android.view.Menu
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.apanda.base.R
import com.apanda.base.base.BaseActivity
import com.apanda.base.base.BaseModel
import com.apanda.base.base.BasePresenter


class WebviewActivity : BaseActivity<BasePresenter<*, *>, BaseModel>(), View.OnClickListener {

    private var webView: WebView? = null


    override fun refresh(msg: Message) {

    }

    override val layoutId: Int
        get() = R.layout.activity_webview

    override fun initPresenter() {

    }

    override fun initView() {
        startProgressDialog()
        val data = intent.getStringExtra("data")
        val title = intent.getStringExtra("title")
        initView(title)

        initWebview(data)
    }


    private fun initWebview(data: String) {

        webView = findViewById(R.id.wv) as WebView


        //        tv_webview_title.setText(title);
        val webSettings = webView!!.settings
        // 设置可以支持缩放
        webSettings.setSupportZoom(true)
        // 设置出现缩放工具
        webSettings.builtInZoomControls = true
        //设置可在大视野范围内上下左右拖动，并且可以任意比例缩放
        webSettings.useWideViewPort = true
        //设置默认加载的可视范围是大视野范围
        webSettings.loadWithOverviewMode = true
        //自适应屏幕
        webSettings.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        webSettings.javaScriptEnabled = true
        webView!!.setWebViewClient(object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url) // 根据传入的参数再去加载新的网页
                return true // 表示当前WebView可以处理打开新网页的请求,不用借助

            }

            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap) {

                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView, url: String) {
                stopProgressDialog()
                super.onPageFinished(view, url)
            }
        })

        webView!!.loadUrl(data)
    }

    private var ischecked: Boolean = false

    private fun initView(title: String) {

        setContentView(R.layout.activity_webview)


        val toolbar = findViewById(R.id.toolbar) as Toolbar


        toolbar.title = title
        toolbar.setTitleTextColor(resources.getColor(R.color.white))
        setSupportActionBar(toolbar)

        //    toolbar.setLeft(R.drawable.star);

        //        toolbar.setNavigationIcon( );
        toolbar.setNavigationIcon(R.mipmap.ic_back)
        toolbar.setNavigationOnClickListener { finish() }
        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_star ->
                    //TODO 收藏
                    if (ischecked) {
                        showLongToast("取消收藏")
                        item.setIcon(R.mipmap.ic_star_outline_white)
                        ischecked = false
                    } else {
                        showLongToast("已收藏,请在收藏中查看!")
                        item.setIcon(R.mipmap.ic_star_white)
                        ischecked = true
                    }
            }
            true
        }


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.webview, menu)
        return true
    }

    override fun onClick(v: View) {
        when (v.id) {

        }//            case R.id.rl_left_first:
        //                onBackPressed();
        //                break;
    }

    /**
     * 保证，在按系统返回键的时候，不是退出程序，而是，返回上一个页面

     * @param keyCode
     * *
     * @param event
     * *
     * @return
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView!!.canGoBack()) {
                webView!!.goBack() //返回上一个页面
            } else {
                onBackPressed()
            }

            return true
        }
        return false
    }

    companion object {


        fun startActivity(mContext: Context, data: String, title: String) {
            val mIntent = Intent(mContext, WebviewActivity::class.java)

            mIntent.putExtra("data", data)
            mIntent.putExtra("title", title)
            mContext.startActivity(mIntent)
        }
    }

}

