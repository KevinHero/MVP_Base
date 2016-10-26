package com.apanda.base.base.daynightmodeutils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.support.v4.view.LayoutInflaterCompat
import android.support.v4.view.LayoutInflaterFactory
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.apanda.base.R

import java.lang.reflect.Field
import java.util.ArrayList

/**
 * 夜间模式控制器

 */
class ChangeModeController private constructor() {

    /**
     * 初始化保存集合
     */
    private fun init() {
        mBackGroundViews = ArrayList<AttrEntity<View>>()
        mOneTextColorViews = ArrayList<AttrEntity<TextView>>()
        mTwoTextColorViews = ArrayList<AttrEntity<TextView>>()
        mThreeTextColorViews = ArrayList<AttrEntity<TextView>>()
        mBackGroundDrawableViews = ArrayList<AttrEntity<View>>()
    }

    /**
     * 初始化夜间控制器
     * @param activity 上下文
     * *
     * @return
     */
    fun init(activity: Activity, mClass: Class<*>): ChangeModeController {
        init()
        LayoutInflaterCompat.setFactory(LayoutInflater.from(activity)) { parent, name, context, attrs ->
            var view: View? = null
            try {
                if (name.indexOf('.') == -1) {
                    if ("View" == name) {
                        view = LayoutInflater.from(context).createView(name, "android.view.", attrs)
                    }
                    if (view == null) {
                        view = LayoutInflater.from(context).createView(name, "android.widget.", attrs)
                    }
                    if (view == null) {
                        view = LayoutInflater.from(context).createView(name, "android.webkit.", attrs)
                    }

                } else {
                    if (view == null) {
                        view = LayoutInflater.from(context).createView(name, null, attrs)
                    }
                }
                if (view != null) {
                    // Log.e("TAG", "name = " + name);
                    for (i in 0..attrs.attributeCount - 1) {
                        //                            Log.e("TAG", attrs.getAttributeName(i) + " , " + attrs.getAttributeValue(i));
                        if (attrs.getAttributeName(i) == ATTR_BACKGROUND) {
                            mBackGroundViews!!.add(AttrEntity<View>(view, getAttr(mClass, attrs.getAttributeValue(i))))
                        }
                        if (attrs.getAttributeName(i) == ATTR_TEXTCOLOR) {
                            mOneTextColorViews!!.add(AttrEntity<TextView>(view as TextView?, getAttr(mClass, attrs.getAttributeValue(i))))
                        }
                        if (attrs.getAttributeName(i) == ATTR_TWO_TEXTCOLOR) {
                            mOneTextColorViews!!.add(AttrEntity<TextView>(view as TextView?, getAttr(mClass, attrs.getAttributeValue(i))))
                        }
                        if (attrs.getAttributeName(i) == ATTR_THREE_TEXTCOLOR) {
                            mOneTextColorViews!!.add(AttrEntity<TextView>(view as TextView?, getAttr(mClass, attrs.getAttributeValue(i))))
                        }
                        if (attrs.getAttributeName(i) == ATTR_BACKGROUND_DRAWABLE) {
                            mBackGroundDrawableViews!!.add(AttrEntity<View>(view, getAttr(mClass, attrs.getAttributeValue(i))))
                        }

                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            view
        }
        return this
    }

    /**
     * 添加背景颜色属性
     * @param view
     * *
     * @param colorId
     * *
     * @return
     */
    fun addBackgroundColor(view: View, colorId: Int): ChangeModeController {
        mBackGroundViews!!.add(AttrEntity(view, colorId))
        return this
    }

    /**
     * 添加背景图片属性
     * @param view
     * *
     * @param drawableId  属性id
     * *
     * @return
     */
    fun addBackgroundDrawable(view: View, drawableId: Int): ChangeModeController {
        mBackGroundDrawableViews!!.add(AttrEntity(view, drawableId))
        return this
    }

    /**
     * 添加一级字体颜色属性
     * @param view
     * *
     * @param colorId 属性id
     * *
     * @return
     */
    fun addTextColor(view: View, colorId: Int): ChangeModeController {
        mOneTextColorViews!!.add(AttrEntity(view as TextView, colorId))
        return this
    }

    /**
     * 添加二级字体颜色属性
     * @param view
     * *
     * @param colorId 属性id
     * *
     * @return
     */
    fun addTwoTextColor(view: View, colorId: Int): ChangeModeController {
        mTwoTextColorViews!!.add(AttrEntity(view as TextView, colorId))
        return this
    }

    /**
     * 添加三级字体颜色属性
     * @param view
     * *
     * @param colorId 属性id
     * *
     * @return
     */
    fun addThreeTextColor(view: View, colorId: Int): ChangeModeController {
        mThreeTextColorViews!!.add(AttrEntity(view as TextView, colorId))
        return this
    }

    internal inner class AttrEntity<T>(var v: T//控件
                                       , var colorId: Int//属性id
    )

    companion object {
        /**
         * 属性背景
         */
        private val ATTR_BACKGROUND = "dayNightBackgroundAttr"
        /**
         * 属性背景图片
         */
        private val ATTR_BACKGROUND_DRAWABLE = "dayNightBackgroundDrawableAttr"
        /**
         * 属性一级字体颜色
         */
        private val ATTR_TEXTCOLOR = "dayNightOneTextColorAttr"
        /**
         * 属性二级字体颜色
         */
        private val ATTR_TWO_TEXTCOLOR = "dayNightTwoTextColorAttr"
        /**
         * 属性三级字体颜色
         */
        private val ATTR_THREE_TEXTCOLOR = "dayNightThreeTextColorAttr"

        private var mBackGroundViews: MutableList<AttrEntity<View>>? = null
        private var mBackGroundDrawableViews: MutableList<AttrEntity<View>>? = null
        private var mOneTextColorViews: MutableList<AttrEntity<TextView>>? = null
        private var mTwoTextColorViews: MutableList<AttrEntity<TextView>>? = null
        private var mThreeTextColorViews: MutableList<AttrEntity<TextView>>? = null

        private var mChangeModeController: ChangeModeController? = null
        val instance: ChangeModeController
            get() {
                if (mChangeModeController == null) {
                    mChangeModeController = ChangeModeController()
                }
                return mChangeModeController
            }

        /**
         * 反射获取文件id
         * @param attrName 属性名称
         * *
         * @return  属性id
         */
        fun getAttr(draw: Class<*>?, attrName: String?): Int {
            if (attrName == null || attrName.trim { it <= ' ' } == "" || draw == null) {
                return R.attr.colorPrimary
            }
            try {
                val field = draw.getDeclaredField(attrName)
                field.isAccessible = true
                return field.getInt(attrName)
            } catch (e: Exception) {
                return R.attr.colorPrimary
            }

        }

        /**
         * 设置当前主题
         * @param ctx  上下文
         * *
         * @param Style_Day  白天
         * *
         * @param Style_Night 夜间
         */
        fun setTheme(ctx: Context, Style_Day: Int, Style_Night: Int) {
            if (ChangeModeHelper.getChangeMode(ctx) == ChangeModeHelper.MODE_DAY) {
                ctx.setTheme(Style_Day)
            } else if (ChangeModeHelper.getChangeMode(ctx) == ChangeModeHelper.MODE_NIGHT) {
                ctx.setTheme(Style_Night)
            }
        }


        /**
         * 动态切换主题
         */
        fun toggleThemeSetting(ctx: Activity) {
            if (ChangeModeHelper.getChangeMode(ctx) == ChangeModeHelper.MODE_DAY) {
                changeNight(ctx, R.style.NightTheme)
            } else if (ChangeModeHelper.getChangeMode(ctx) == ChangeModeHelper.MODE_NIGHT) {
                changeDay(ctx, R.style.DayTheme)
            }
        }

        /**

         * @param ctx 上下文
         * *
         * @param style 切换style
         */
        fun changeNight(ctx: Activity, style: Int) {
            if (mBackGroundDrawableViews == null || mOneTextColorViews == null || mBackGroundViews == null) {
                throw RuntimeException("请先调用init()初始化方法!")
            }
            ChangeModeHelper.setChangeMode(ctx, ChangeModeHelper.MODE_NIGHT)
            ctx.setTheme(style)
            showAnimation(ctx)
            refreshUI(ctx)
        }

        /**

         * @param ctx 上下文
         * *
         * @param style 切换style
         */
        fun changeDay(ctx: Activity, style: Int) {
            if (mBackGroundDrawableViews == null || mOneTextColorViews == null || mTwoTextColorViews == null || mThreeTextColorViews == null || mBackGroundViews == null) {
                throw RuntimeException("请先调用init()初始化方法!")
            }
            ChangeModeHelper.setChangeMode(ctx, ChangeModeHelper.MODE_DAY)
            ctx.setTheme(style)
            showAnimation(ctx)
            refreshUI(ctx)
        }


        /**
         * 刷新UI界面
         * @param ctx  上下文
         */
        private fun refreshUI(ctx: Activity) {

            val typedValue = TypedValue()
            val theme = ctx.theme

            theme.resolveAttribute(R.attr.colorPrimary, typedValue, true)
            val view = ctx.findViewById(R.id.action_bar)
            view?.setBackgroundResource(typedValue.resourceId)
            for (entity in mBackGroundViews!!) {
                theme.resolveAttribute(entity.colorId, typedValue, true)
                entity.v.setBackgroundResource(typedValue.resourceId)
            }

            for (entity in mBackGroundDrawableViews!!) {
                theme.resolveAttribute(entity.colorId, typedValue, true)
                entity.v.setBackgroundResource(typedValue.resourceId)
            }

            for (entity in mOneTextColorViews!!) {
                theme.resolveAttribute(entity.colorId, typedValue, true)
                entity.v.setTextColor(ctx.resources.getColor(typedValue.resourceId))
            }
            for (entity in mOneTextColorViews!!) {
                theme.resolveAttribute(entity.colorId, typedValue, true)
                entity.v.setTextColor(ctx.resources.getColor(typedValue.resourceId))
            }
            //refreshStatusBar(ctx);
        }


        /**
         * 获取某个属性的TypedValue
         * @param ctx 上下文
         * *
         * @param attr  属性id
         * *
         * @return
         */
        fun getAttrTypedValue(ctx: Activity, attr: Int): TypedValue {
            val typedValue = TypedValue()
            val theme = ctx.theme
            theme.resolveAttribute(attr, typedValue, true)
            return typedValue
        }


        /**
         * 刷新 StatusBar
         * @param ctx  上下文
         */
        private fun refreshStatusBar(ctx: Activity) {
            if (Build.VERSION.SDK_INT >= 21) {
                val typedValue = TypedValue()
                val theme = ctx.theme
                theme.resolveAttribute(R.attr.colorPrimaryDark, typedValue, true)
                ctx.window.statusBarColor = ctx.resources.getColor(typedValue.resourceId)
            }
        }

        /**
         * 展示切换动画
         */
        private fun showAnimation(ctx: Activity) {
            val decorView = ctx.window.decorView
            val cacheBitmap = getCacheBitmapFromView(decorView)
            if (decorView is ViewGroup && cacheBitmap != null) {
                val view = View(ctx)
                view.setBackgroundDrawable(BitmapDrawable(ctx.resources, cacheBitmap))

                val layoutParam = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT)
                decorView.addView(view, layoutParam)

                val objectAnimator = ValueAnimator.ofFloat(1f, 0f)//view, "alpha",
                objectAnimator.duration = 500
                objectAnimator.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                        decorView.removeView(view)

                    }
                })
                objectAnimator.addUpdateListener { animation ->
                    val alpha = animation.animatedValue as Float
                    view.alpha = alpha
                }
                objectAnimator.start()
            }
        }

        /**
         * 获取一个 View 的缓存视图

         * @param view
         * *
         * @return
         */
        private fun getCacheBitmapFromView(view: View): Bitmap? {
            val drawingCacheEnabled = true
            view.isDrawingCacheEnabled = drawingCacheEnabled
            view.buildDrawingCache(drawingCacheEnabled)
            val drawingCache = view.drawingCache
            val bitmap: Bitmap?
            if (drawingCache != null) {
                bitmap = Bitmap.createBitmap(drawingCache)
                view.isDrawingCacheEnabled = false
            } else {
                bitmap = null
            }
            return bitmap
        }

        /**
         * 视图销毁时调用
         */
        fun onDestory() {
            mBackGroundViews!!.clear()
            mOneTextColorViews!!.clear()
            mTwoTextColorViews!!.clear()
            mThreeTextColorViews!!.clear()
            mBackGroundDrawableViews!!.clear()
            mBackGroundViews = null
            mOneTextColorViews = null
            mTwoTextColorViews = null
            mThreeTextColorViews = null
            mBackGroundDrawableViews = null
            mChangeModeController = null
        }
    }
}
