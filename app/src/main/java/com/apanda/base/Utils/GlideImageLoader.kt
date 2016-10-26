package com.apanda.base.Utils

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView

import com.apanda.base.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.lzy.imagepicker.loader.ImageLoader
import com.lzy.ninegrid.NineGridView

import java.io.File

/**
 * ================================================
 * 作    者：jeasonlzy（廖子尧）Github地址：https://github.com/jeasonlzy0216
 * 版    本：1.0
 * 创建日期：16/9/5
 * 描    述：
 * 修订历史：
 * ================================================
 */
class GlideImageLoader private constructor() : ImageLoader, NineGridView.ImageLoader {

    override fun onDisplayImage(context: Context, imageView: ImageView, url: String) {
        Glide.with(context).load(url)//
                .placeholder(R.mipmap.default_image)//
                .error(R.mipmap.default_image)//
                .diskCacheStrategy(DiskCacheStrategy.ALL)//
                .into(imageView)
    }


    fun onDisplayImage(context: Context, imageView: ImageView, url: Bitmap) {
        Glide.with(context).load(url)//
                .placeholder(R.mipmap.default_image)//
                .error(R.mipmap.default_image)//
                .diskCacheStrategy(DiskCacheStrategy.ALL)//
                .into(imageView)
    }

    fun onDisplayImage(context: Context, imageView: ImageView, id: Int) {
        Glide.with(context).load(id)//
                .placeholder(R.mipmap.default_image)//
                .error(R.mipmap.default_image)//
                .diskCacheStrategy(DiskCacheStrategy.ALL)//
                .into(imageView)
    }

    override fun getCacheImage(url: String): Bitmap? {
        return null
    }

    override fun displayImage(activity: Activity, path: String, imageView: ImageView, width: Int, height: Int) {
        Glide.with(activity).load(File(path))//
                .placeholder(R.mipmap.default_image)//
                .error(R.mipmap.default_image)//
                .diskCacheStrategy(DiskCacheStrategy.ALL)//
                .into(imageView)
    }


    override fun clearMemoryCache() {
    }

    companion object {

        private var _singleInstance: GlideImageLoader? = null

        val instance: GlideImageLoader
            get() {
                synchronized(GlideImageLoader::class.java) {
                    if (_singleInstance == null) {
                        _singleInstance = GlideImageLoader()
                    }
                }
                return _singleInstance!!
            }

        fun displayRound(context: Context, imageView: ImageView?, url: String) {
            if (imageView == null) {
                throw IllegalArgumentException("argument error")
            }
            Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).error(R.mipmap.toux2).centerCrop().transform(GlideRoundTransformUtil(context)).into(imageView)
        }
    }
}
