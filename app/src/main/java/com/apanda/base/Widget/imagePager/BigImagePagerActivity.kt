package com.apanda.base.Widget.imagePager

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Message
import android.os.Parcelable
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.text.TextUtils
import android.view.Gravity
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.ProgressBar

import com.apanda.base.R
import com.apanda.base.Widget.commonwidget.ViewPagerFixed
import com.apanda.base.base.BaseActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

import java.util.ArrayList

import uk.co.senab.photoview.PhotoView
import uk.co.senab.photoview.PhotoViewAttacher

/**
 * 查看大图 glide
 * Created by jaydenxiao
 */
class BigImagePagerActivity : BaseActivity<*, *>() {
    private val guideViewList = ArrayList<View>()
    private var guideGroup: LinearLayout? = null


    /**
     * 监听返回键

     * @param keyCode
     * *
     * @param event
     * *
     * @return
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            this@BigImagePagerActivity.overridePendingTransition(R.anim.fade_in,
                    R.anim.fade_out)
            this@BigImagePagerActivity.finish()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun refresh(msg: Message) {

    }

    override val layoutId: Int
        get() = R.layout.act_image_pager

    override fun initView() {
        //设置透明状态栏
        SetTranslanteBar()

        val viewPager = findViewById(R.id.pager) as ViewPagerFixed
        guideGroup = findViewById(R.id.guideGroup) as LinearLayout

        val startPos = intent.getIntExtra(INTENT_POSITION, 0)
        val imgUrls = intent.getStringArrayListExtra(INTENT_IMGURLS)

        val mAdapter = ImageAdapter(this)
        mAdapter.setDatas(imgUrls)
        viewPager.adapter = mAdapter
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                for (i in guideViewList.indices) {
                    guideViewList[i].isSelected = i == position
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })
        viewPager.currentItem = startPos

        addGuideView(guideGroup, startPos, imgUrls)
    }

    override fun initPresenter() {

    }

    private fun addGuideView(guideGroup: LinearLayout, startPos: Int, imgUrls: ArrayList<String>?) {
        if (imgUrls != null && imgUrls.size > 0) {
            guideViewList.clear()
            for (i in imgUrls.indices) {
                if (!TextUtils.isEmpty(imgUrls[i])) {
                    val view = View(this)
                    view.setBackgroundResource(R.drawable.selector_guide_bg)
                    view.isSelected = i == startPos
                    val layoutParams = LinearLayout.LayoutParams(resources.getDimensionPixelSize(R.dimen.gudieview_width),
                            resources.getDimensionPixelSize(R.dimen.gudieview_heigh))
                    layoutParams.setMargins(10, 0, 0, 0)
                    guideGroup.addView(view, layoutParams)
                    guideViewList.add(view)
                }
            }
        }
    }

    private inner class ImageAdapter(private val context: Context) : PagerAdapter() {

        private var datas: MutableList<String>? = ArrayList()
        private val inflater: LayoutInflater

        fun setDatas(datas: MutableList<String>?) {
            if (datas != null)
                this.datas = datas
            if (datas!!.contains(""))
                this.datas!!.remove("")
        }

        init {
            this.inflater = LayoutInflater.from(context)

        }

        override fun getCount(): Int {
            if (datas == null) return 0
            return datas!!.size
        }


        override fun instantiateItem(container: ViewGroup, position: Int): Any? {
            val view = inflater.inflate(R.layout.item_pager_image, container, false)
            if (view != null) {

                val imgurl = datas!![position]
                if (TextUtils.isEmpty(imgurl)) {
                    return null
                }


                val imageView = view.findViewById(R.id.image) as PhotoView

                //单击图片退出
                imageView.onPhotoTapListener = { view, x, y ->
                    this@BigImagePagerActivity.overridePendingTransition(R.anim.act_fade_in_center,
                            R.anim.act_fade_out_center)
                    this@BigImagePagerActivity.finish()
                }

                //loading
                val loading = ProgressBar(context)
                val loadingLayoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT)
                loadingLayoutParams.gravity = Gravity.CENTER
                loading.layoutParams = loadingLayoutParams
                (view as FrameLayout).addView(loading)


                loading.visibility = View.VISIBLE

                Glide.with(context).load(imgurl).diskCacheStrategy(DiskCacheStrategy.ALL).error(R.mipmap.ic_empty_picture).thumbnail(0.1f).listener(object : RequestListener<String, GlideDrawable> {
                    override fun onException(e: Exception, model: String, target: Target<GlideDrawable>, isFirstResource: Boolean): Boolean {
                        loading.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(resource: GlideDrawable, model: String, target: Target<GlideDrawable>, isFromMemoryCache: Boolean, isFirstResource: Boolean): Boolean {
                        loading.visibility = View.GONE
                        return false
                    }
                }).into(imageView)

                container.addView(view, 0)
            }
            return view
        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            container.removeView(`object` as View)
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view == `object`
        }

        override fun restoreState(state: Parcelable?, loader: ClassLoader?) {
        }

        override fun saveState(): Parcelable? {
            return null
        }


    }

    companion object {
        val INTENT_IMGURLS = "imgurls"
        val INTENT_POSITION = "position"

        fun startImagePagerActivity(activity: Activity, imgUrls: List<String>, position: Int) {
            val intent = Intent(activity, BigImagePagerActivity::class.java)
            intent.putStringArrayListExtra(INTENT_IMGURLS, ArrayList(imgUrls))
            intent.putExtra(INTENT_POSITION, position)
            activity.startActivity(intent)
            activity.overridePendingTransition(R.anim.fade_in,
                    R.anim.fade_out)
        }
    }
}
