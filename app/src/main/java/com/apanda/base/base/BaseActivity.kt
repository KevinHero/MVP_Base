package com.apanda.base.base


import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.*
import butterknife.ButterKnife
import com.apanda.base.R
import com.apanda.base.Utils.HttpDownloader
import com.apanda.base.Utils.SwipeBackUtils.SwipeWindowHelper
import com.apanda.base.Utils.TUtil
import com.apanda.base.Utils.ToastUtils
import com.apanda.base.Widget.commonwidget.LoadingDialog
import com.apanda.base.Widget.commonwidget.StatusBarCompat
import com.apanda.base.base.baseapp.AppManager
import com.apanda.base.base.baserx.RxManager
import com.apanda.base.base.daynightmodeutils.ChangeModeController
import rx.Subscription
import rx.subscriptions.CompositeSubscription
import java.io.IOException
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * 基类
 */

/***************
 * 使用例子
 */
//1.mvp模式
//public class SampleActivity extends BaseActivity<NewsChanelPresenter, NewsChannelModel>implements NewsChannelContract.View {
//    @Override
//    public int getLayoutId() {
//        return R.layout.activity_news_channel;
//    }
//
//    @Override
//    public void initPresenter() {
//        mPresenter.setVM(this, mModel);
//    }
//
//    @Override
//    public void initView() {
//    }
//}
//2.普通模式
//public class SampleActivity extends BaseActivity {
//    @Override
//    public int getLayoutId() {
//        return R.layout.activity_news_channel;
//    }
//
//    @Override
//    public void initPresenter() {
//    }
//
//    @Override
//    public void initView() {
//    }
//}
@Suppress("UNUSED_VALUE")
abstract class BaseActivity<T : BasePresenter<*, *>, E : BaseModel> : AppCompatActivity() {
    var mPresenter: T? = null
    var mModel: E = null!!
    var _instance: Context = null!!
    var mRxManager: RxManager = null!!

    var savedInstanceState: Bundle? = null

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mRxManager = RxManager()
        doBeforeSetcontentView()
        setContentView(layoutId)
        ButterKnife.bind(this)
        _instance = this
        mPresenter = TUtil.getT<T>(this, 0)
        mModel = TUtil.getT<E>(this, 1)!!
        if (mPresenter != null) {
            mPresenter!!.mContext = this
        }
        this.initPresenter()
        this.initView()

        executorService = Executors.newFixedThreadPool(2)

    }

    protected abstract fun refresh(msg: Message)

    private var executorService: ExecutorService? = null


    protected val handler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {


            refresh(msg)
        }

    }

    /**
     * 开启新线程请求班结数据网络
     */
    protected fun startNewThreadRequestData(url: String, REQUESTCODE: Int) {
        executorService!!.execute {
            var json: String? = null
            try {
                json = HttpDownloader.instance.run(url)
            } catch (e: IOException) {
                e.printStackTrace()
            }

            val msg = handler.obtainMessage()
            msg.what = REQUESTCODE
            msg.obj = json
            msg.sendToTarget()
        }
    }


    /**
     * 设置layout前配置
     */
    private fun doBeforeSetcontentView() {
        //设置昼夜主题
        initTheme()
        // 把actvity放到application栈中管理
        AppManager.appManager.addActivity(this)
        // 无标题
        //   requestWindowFeature(Window.FEATURE_NO_TITLE);

        // 设置竖屏
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        // 默认着色状态栏
        SetStatusBarColor()

    }

    /*********************
     * 子类实现
     */
    //获取布局文件
    abstract val layoutId: Int

    //简单页面无需mvp就不用管此方法即可,完美兼容各种实际场景的变通
    abstract fun initPresenter()

    //初始化view
    abstract fun initView()


    private var mSwipeWindowHelper: SwipeWindowHelper? = null

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (!supportSlideBack()) {
            return super.dispatchTouchEvent(ev)
        }

        if (mSwipeWindowHelper == null) {
            mSwipeWindowHelper = SwipeWindowHelper(window)
        }
        return mSwipeWindowHelper!!.processTouchEvent(ev) || super.dispatchTouchEvent(ev)
    }

    /**
     * 是否支持滑动返回

     * @return
     */
    protected open fun supportSlideBack(): Boolean {
        return true
    }


    protected val tag: String
        get() = this.toString()


    fun setStatusBarLightMode(window: Window, isFontColorDark: Boolean): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (isFontColorDark) {
                // 沉浸式
                //                activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                //非沉浸式
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                //非沉浸式
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
            }
            return true
        }
        return false
    }


    /**
     * 设置主题
     */
    private fun initTheme() {
        ChangeModeController.setTheme(this, R.style.DayTheme, R.style.NightTheme)
    }

    /**
     * 着色状态栏（4.4以上系统有效）
     */
    protected fun SetStatusBarColor() {
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary))
        //        setStatusBarLightMode(getWindow(), true);
        //        FlymeSetStatusBarLightMode(getWindow(), true);
        //        MIUISetStatusBarLightMode(getWindow(), true);
    }

    /**
     * 着色状态栏（4.4以上系统有效）
     */
    protected fun SetStatusBarColor(color: Int) {
        StatusBarCompat.setStatusBarColor(this, color)
    }

    /**
     * 沉浸状态栏（4.4以上系统有效）
     */
    protected fun SetTranslanteBar() {
        StatusBarCompat.translucentStatusBar(this)
    }

    /**
     * 通过Class跳转界面
     */
    fun startActivityForResult(cls: Class<*>, requestCode: Int) {
        startActivityForResult(cls, null, requestCode)
    }

    /**
     * 含有Bundle通过Class跳转界面
     */
    fun startActivityForResult(cls: Class<*>, bundle: Bundle?,
                               requestCode: Int) {
        val intent = Intent()
        intent.setClass(this, cls)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivityForResult(intent, requestCode)
    }

    /**
     * 含有Bundle通过Class跳转界面
     */
    @JvmOverloads fun startActivity(cls: Class<*>, bundle: Bundle? = null) {
        val intent = Intent()
        intent.setClass(this, cls)
        if (bundle != null) {
            intent.putExtras(bundle)
        }
        startActivity(intent)
    }

    /**
     * 开启浮动加载进度条
     */
    fun startProgressDialog() {
        LoadingDialog.showDialogForLoading(this)
    }

    /**
     * 开启浮动加载进度条

     * @param msg
     */
    fun startProgressDialog(msg: String) {
        LoadingDialog.showDialogForLoading(this, msg, true)
    }

    /**
     * 停止浮动加载进度条
     */
    fun stopProgressDialog() {
        LoadingDialog.cancelDialogForLoading()
    }

    /**
     * 短暂显示Toast提示(来自String)
     */
    fun showShortToast(text: String) {
        ToastUtils.showShort(text)
    }

    /**
     * 短暂显示Toast提示(id)
     */
    fun showShortToast(resId: Int) {
        ToastUtils.showShort(resId)
    }

    /**
     * 长时间显示Toast提示(来自res)
     */
    fun showLongToast(resId: Int) {
        ToastUtils.showLong(resId)
    }

    /**
     * 长时间显示Toast提示(来自String)
     */
    fun showLongToast(text: String) {
        ToastUtils.showLong(text)
    }

    /**
     * 带图片的toast

     * @param text
     * *
     * @param res
     */
    fun showToastWithImg(text: String, res: Int) {
        ToastUtils.showToastWithImg(text, res)
    }

    /**
     * 网络访问错误提醒
     */
    fun showNetErrorTip() {
        ToastUtils.showToastWithImg(getText(R.string.net_error).toString(), R.mipmap.ic_wifi_off)
    }

    fun showNetErrorTip(error: String) {
        ToastUtils.showToastWithImg(error, R.mipmap.ic_wifi_off)
    }

    override fun onResume() {
        super.onResume()

    }

    override fun onPause() {
        super.onPause()
    }


    override fun onDestroy() {
        super.onDestroy()
        if (mPresenter != null)
            mPresenter!!.onDestroy()
        mRxManager.clear()
        ButterKnife.unbind(this)
        AppManager.appManager.finishActivity(this)
    }


    private var compositeSubscription: CompositeSubscription? = null

    fun addSubscribe(subscription: Subscription) {
        if (compositeSubscription == null) {
            compositeSubscription = CompositeSubscription()
        }
        compositeSubscription!!.add(subscription)
    }

    fun unSubscribe() {
        if (compositeSubscription != null) compositeSubscription!!.unsubscribe()
    }


    /**
     * 初始化 Toolbar
     */
    @JvmOverloads fun initToolBar(toolbar: Toolbar, title: String, title_color: Int = 0) {
        toolbar.title = title
        if (title_color != 0) {
            toolbar.setTitleTextColor(resources.getColor(title_color))
        }

        setSupportActionBar(toolbar)
        toolbar.setNavigationIcon(R.mipmap.ic_back)
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)

    }

    //    public void initToolBar(Toolbar toolbar, boolean homeAsUpEnabled, int resTitle) {
    //        initToolBar(toolbar, homeAsUpEnabled, getString(resTitle));
    //    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home// 点击返回图标事件
            -> {
                finish()
                return super.onOptionsItemSelected(item)
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    companion object {


        /**
         * 设置状态栏图标为深色和魅族特定的文字风格，Flyme4.0以上
         * 可以用来判断是否为Flyme用户

         * @param window 需要设置的窗口
         * *
         * @param dark   是否把状态栏字体及图标颜色设置为深色
         * *
         * @return boolean 成功执行返回true
         */
        fun FlymeSetStatusBarLightMode(window: Window?, dark: Boolean): Boolean {
            var result = false
            if (window != null) {
                try {
                    val lp = window.attributes
                    val darkFlag = WindowManager.LayoutParams::class.java.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON")
                    val meizuFlags = WindowManager.LayoutParams::class.java.getDeclaredField("meizuFlags")
                    darkFlag.isAccessible = true
                    meizuFlags.isAccessible = true
                    val bit = darkFlag.getInt(null)
                    var value = meizuFlags.getInt(lp)
                    if (dark) {
                        value = value or bit
                    } else {
                        value = value and bit.inv()
                    }
                    meizuFlags.setInt(lp, value)
                    window.attributes = lp
                    result = true
                } catch (e: Exception) {

                }

            }
            return result
        }

        /**
         * 设置状态栏字体图标为深色，需要MIUIV6以上

         * @param window 需要设置的窗口
         * *
         * @param dark   是否把状态栏字体及图标颜色设置为深色
         * *
         * @return boolean 成功执行返回true
         */
        fun MIUISetStatusBarLightMode(window: Window?, dark: Boolean): Boolean {
            var result = false
            if (window != null) {
                val clazz = window.javaClass
                try {
                    var darkModeFlag = 0
                    val layoutParams = Class.forName("android.view.MiuiWindowManager\$LayoutParams")
                    val field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE")
                    darkModeFlag = field.getInt(layoutParams)
                    val extraFlagField = clazz.getMethod("setExtraFlags", Integer.TYPE, Integer.TYPE)
                    if (dark) {
                        extraFlagField.invoke(window, darkModeFlag, darkModeFlag)//状态栏透明且黑色字体
                    } else {
                        extraFlagField.invoke(window, 0, darkModeFlag)//清除黑色字体
                    }
                    result = true
                } catch (e: Exception) {

                }

            }
            return result
        }
    }

}
/**
 * 通过Class跳转界面
 */
