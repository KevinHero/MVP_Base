package com.apanda.base.Utils

import android.content.Context
import android.os.Handler
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout

import com.apanda.base.R


/*
*
 * 广告轮播viewpager管理
 *
*/

//*******************************使用说明***************************************
//使用说明：
//       AdViewpagerUtil   adViewpagerUtil = new AdViewpagerUtil(getAppContext(), viewPagerTop, dotLayout, urls);
//adViewpagerUtil.setOnAdItemClickListener(new AdViewpagerUtil.OnAdItemClickListener(){
//@Override
//public void onItemClick(View v,int position,String url){
//        }
//        });

//生命周期控制
//@Override
//public void onPause() {
//        super.onPause();
//        if(adViewpagerUtil!=null) {
//        adViewpagerUtil.stopLoopViewPager();
//        }
//        }
//
//@Override
//public void onResume() {
//        super.onResume();
//        if(adViewpagerUtil!=null) {
//        adViewpagerUtil.startLoopViewPager();
//        }
//        }
//
//@Override
//public void onDestroy() {
//        super.onDestroy();
//        if(adViewpagerUtil!=null) {
//        adViewpagerUtil.destroyAdViewPager();
//        }
//        }


// 布局layout:
//         <RelativeLayout
//         android:id="@+id/rl_adroot"
//         android:layout_width="wrap_content"
//         android:layout_height="300dp"
//         >
//         <android.support.v4.view.ViewPager android:id="@+id/viewpager"
//         android:layout_width="match_parent"
//         android:layout_height="300dp"
//         android:background="#3d3c3c">
//         </android.support.v4.view.ViewPager>
//         <LinearLayout
//         android:id="@+id/ly_dots"
//         android:layout_width="wrap_content"
//         android:layout_height="wrap_content"
//         android:orientation="horizontal"
//         android:layout_alignParentBottom="true"
//         android:layout_centerHorizontal="true"
//         android:layout_marginBottom="20dp">
//         </LinearLayout>
//         </RelativeLayout>

class AdViewpagerUtil {

    private var context: Context? = null
    private var viewPager: ViewPager? = null
    private var mimageViewPagerAdapter: AdPagerAdapter? = null
    private var mImageViews: Array<ImageView>? = null
    private var urls: Array<String>? = null
    private var dotlayout: LinearLayout? = null
    private var dotViews: Array<ImageView>? = null

    private var isRight = true // 判断viewpager是不是向右滑动
    private var lastPosition = 1 // 记录viewpager从哪个页面滑动到当前页面，从而区分滑动方向
    private var autoIndex = 1 // 自动轮询时自增坐标，能确定导航圆点的位置
    private var currentIndex = 0 //当前item
    private val delayTime = 5000 //自动轮播的时间间隔
    private var imgsize = 0 //图片的数量，item的数量
    private var isLoop = false//轮播开关

    private var onAdPageChangeListener: OnAdPageChangeListener? = null //pagechange回调
    private var onAdItemClickListener: OnAdItemClickListener? = null //点击事件回调

    private val dotsize = 8 //小圆点的大小宽度
    private val dotoffset = 4 //小圆点的间距

    /**
     * 不带小圆点

     * @param context
     * *
     * @param viewPager
     * *
     * @param urls
     */
    constructor(context: Context, viewPager: ViewPager, urls: Array<String>) {
        this.context = context
        this.viewPager = viewPager
        this.urls = urls
        initVps()
    }

    /**
     * 有小圆点

     * @param context
     * *
     * @param viewPager
     * *
     * @param dotlayout
     * *
     * @param dotsize
     * *
     * @param dotoffset
     * *
     * @param urls
     */
    constructor(context: Context, viewPager: ViewPager, dotlayout: LinearLayout, dotsize: Int, dotoffset: Int, urls: Array<String>) {
        this.context = context
        this.viewPager = viewPager
        this.dotlayout = dotlayout
        this.dotsize = dotsize
        this.urls = urls
        initVps()
    }

    constructor(context: Context, viewPager: ViewPager, dotlayout: LinearLayout, urls: Array<String>) {
        this.context = context
        this.viewPager = viewPager
        this.dotlayout = dotlayout
        this.urls = urls
        initVps()
    }

    /**
     * 监听滑动

     * @param onAdPageChangeListener
     */
    fun setOnAdPageChangeListener(onAdPageChangeListener: OnAdPageChangeListener) {
        this.onAdPageChangeListener = onAdPageChangeListener
    }

    /**
     * 监听点击

     * @param onAdItemClickListener
     */
    fun setOnAdItemClickListener(onAdItemClickListener: OnAdItemClickListener) {
        this.onAdItemClickListener = onAdItemClickListener
    }

    /**
     * 初始化图片

     * @param urls
     */
    private fun initAdimgs(urls: Array<String>) {
        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        val length = urls.size + 2
        mImageViews = arrayOfNulls<ImageView>(length)
        for (i in 0..length - 1) {
            val imageView = ImageView(context)
            imageView.layoutParams = params
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            mImageViews[i] = imageView
        }
        setImg(length, urls)
    }

    /**
     * 显示图片

     * @param length
     * *
     * @param urls
     */
    private fun setImg(length: Int, urls: Array<String>) {
        if (urls.size > 0) {
            imgsize = length
            for (i in 0..length - 1) {
                if (i < length - 2) {
                    val index = i
                    val url = urls[i]
                    ImageLoaderUtils.display(context, mImageViews!![i + 1], url)
                    mImageViews!![i + 1].setOnClickListener {
                        // TODO Auto-generated method stub
                        if (onAdItemClickListener != null) {
                            onAdItemClickListener!!.onItemClick(mImageViews!![index + 1], index, url)
                        }
                    }
                }
            }
            ImageLoaderUtils.display(context, mImageViews!![0], urls[urls.size - 1])
            ImageLoaderUtils.display(context, mImageViews!![length - 1], urls[0])
        }
    }

    /**
     * 初始化viewpager
     */
    fun initVps() {
        initAdimgs(urls)
        initDots(urls!!.size)
        mimageViewPagerAdapter = AdPagerAdapter(context, mImageViews)
        viewPager!!.adapter = mimageViewPagerAdapter
        viewPager!!.offscreenPageLimit = mImageViews!!.size
        startLoopViewPager()
        viewPager!!.setOnTouchListener { v, event ->
            // TODO Auto-generated method stub
            val action = event.action
            when (action) {
                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                    isLoop = true
                    stopLoopViewPager()
                }
                MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                    isLoop = false
                    startLoopViewPager()
                }
                else -> {
                }
            }
            false
        }
        viewPager!!.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrollStateChanged(arg0: Int) {
                // TODO Auto-generated method stub
                if (isRight) {
                    if (arg0 != 1) {
                        if (lastPosition == 0) {
                            viewPager!!.setCurrentItem(imgsize - 2, false)
                        } else if (lastPosition == imgsize - 1) {
                            viewPager!!.setCurrentItem(1, false)
                        }
                    }
                }

                if (onAdPageChangeListener != null) {
                    onAdPageChangeListener!!.onPageScrollStateChanged(arg0)
                }
            }

            override fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int) {
                // TODO Auto-generated method stub
                if (!isRight) {
                    if (arg1 < 0.01) {
                        if (arg0 == 0) {
                            viewPager!!.setCurrentItem(imgsize - 2, false)
                        } else if (arg0 == imgsize - 1) {
                            viewPager!!.setCurrentItem(1, false)
                        }
                    }
                }
                if (onAdPageChangeListener != null) {
                    onAdPageChangeListener!!.onPageScrolled(arg0, arg1, arg2)
                }
            }

            override fun onPageSelected(arg0: Int) {
                // TODO Auto-generated method stub
                autoIndex = arg0
                if (lastPosition < arg0 && lastPosition != 0) {
                    isRight = true
                } else if (lastPosition == imgsize - 1) {
                    isRight = true
                }
                if (lastPosition > arg0 && lastPosition != imgsize - 1) {
                    isRight = false
                } else if (lastPosition == 0) {
                    isRight = false
                }
                lastPosition = arg0

                if (arg0 == 0) {
                    currentIndex = imgsize - 2
                } else if (arg0 == imgsize - 1) {
                    currentIndex = 1
                } else {
                    currentIndex = arg0
                }

                for (i in dotViews!!.indices) {
                    if (i == currentIndex - 1) {
                        dotViews!![i].isSelected = true
                    } else {
                        dotViews!![i].isSelected = false
                    }
                }

                if (onAdPageChangeListener != null) {
                    onAdPageChangeListener!!.onPageSelected(arg0)
                }
            }

        })
        viewPager!!.currentItem = 1// 初始化时设置显示第一页（ViewPager中索引为1）
    }

    /**
     * 初始化标识点

     * @param length
     */
    fun initDots(length: Int) {
        if (dotlayout == null)
            return
        dotlayout!!.removeAllViews()
        val mParams = LinearLayout.LayoutParams(dip2px(context, dotsize.toFloat()), dip2px(context, dotsize.toFloat()))
        mParams.setMargins(dip2px(context, dotoffset.toFloat()), 0, dip2px(context, dotoffset.toFloat()), 0)//设置小圆点左右之间的间隔

        dotViews = arrayOfNulls<ImageView>(length)

        for (i in 0..length - 1) {
            val imageView = ImageView(context)
            imageView.layoutParams = mParams
            imageView.setImageResource(R.drawable.dot_selector)
            if (i == 0) {
                imageView.isSelected = true//默认启动时，选中第一个小圆点
            } else {
                imageView.isSelected = false
            }
            dotViews[i] = imageView//得到每个小圆点的引用，用于滑动页面时，（onPageSelected方法中）更改它们的状态。
            dotlayout!!.addView(imageView)//添加到布局里面显示
        }
    }

    internal var handler: Handler? = Handler()
    internal var runnable: Runnable = object : Runnable {
        override fun run() {
            // 要做的事情
            if (viewPager!!.childCount > 0) {
                handler!!.postDelayed(this, delayTime.toLong())
                autoIndex++
                viewPager!!.setCurrentItem(autoIndex % imgsize, true)
            }
        }
    }

    /**
     * 开始自动轮播
     */
    private fun startLoopViewPager() {
        if (!isLoop && viewPager != null && handler != null) {
            handler!!.postDelayed(runnable, delayTime.toLong())// 每两秒执行一次runnable.
            isLoop = true
        }

    }

    /**
     * 停止自动轮播
     */
    private fun stopLoopViewPager() {
        if (isLoop && viewPager != null && handler != null) {
            handler!!.removeCallbacks(runnable)
            isLoop = false
        }
    }

    interface OnAdItemClickListener {
        fun onItemClick(v: View, position: Int, url: String)
    }

    interface OnAdPageChangeListener {
        fun onPageScrollStateChanged(arg0: Int)

        fun onPageScrolled(arg0: Int, arg1: Float, arg2: Int)

        fun onPageSelected(arg0: Int)
    }

    fun dip2px(context: Context, dipValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dipValue * scale + 0.5f).toInt()
    }


    /**
     * 广告适配器
     */
    private class AdPagerAdapter(private val context: Context, private val imageViews: Array<ImageView>) : PagerAdapter() {
        private val size: Int

        init {
            size = imageViews.size
        }

        override fun getCount(): Int {
            return imageViews.size
        }

        override fun isViewFromObject(arg0: View, arg1: Any): Boolean {
            return arg0 === arg1
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            //		((ViewPager) container).removeView((View) object);// 完全溢出view,避免数据多时出现重复现象
            container.removeView(imageViews[position])//删除页卡
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            container.addView(imageViews[position], 0)
            return imageViews[position]
        }
    }

}
