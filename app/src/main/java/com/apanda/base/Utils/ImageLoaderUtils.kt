package com.apanda.base.Utils

import android.content.Context
import android.widget.ImageView

import com.apanda.base.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy

import java.io.File

/**
 * Description : 图片加载工具类 使用glide框架封装
 */
object ImageLoaderUtils {

    fun display(context: Context, imageView: ImageView?, url: String, placeholder: Int, error: Int) {
        if (imageView == null) {
            throw IllegalArgumentException("argument error")
        }
        Glide.with(context).load(url).placeholder(placeholder).error(error).crossFade().into(imageView)
    }

    fun display(context: Context, imageView: ImageView?, url: String) {
        if (imageView == null) {
            throw IllegalArgumentException("argument error")
        }
        Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop().placeholder(R.mipmap.ic_image_loading).error(R.mipmap.ic_empty_picture).crossFade().into(imageView)
    }

    fun display(context: Context, imageView: ImageView?, url: File) {
        if (imageView == null) {
            throw IllegalArgumentException("argument error")
        }
        Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop().placeholder(R.mipmap.ic_image_loading).error(R.mipmap.ic_empty_picture).crossFade().into(imageView)
    }

    fun displaySmallPhoto(context: Context, imageView: ImageView?, url: String) {
        if (imageView == null) {
            throw IllegalArgumentException("argument error")
        }
        Glide.with(context).load(url).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.mipmap.ic_image_loading).error(R.mipmap.ic_empty_picture).thumbnail(0.5f).into(imageView)
    }

    fun displayBigPhoto(context: Context, imageView: ImageView?, url: String) {
        if (imageView == null) {
            throw IllegalArgumentException("argument error")
        }
        Glide.with(context).load(url).asBitmap().format(DecodeFormat.PREFER_ARGB_8888).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.mipmap.ic_image_loading).error(R.mipmap.ic_empty_picture).into(imageView)
    }

    fun display(context: Context, imageView: ImageView?, url: Int) {
        if (imageView == null) {
            throw IllegalArgumentException("argument error")
        }
        Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).centerCrop().placeholder(R.mipmap.ic_image_loading).error(R.mipmap.ic_empty_picture).crossFade().into(imageView)
    }

    fun displayRound(context: Context, imageView: ImageView?, url: String) {
        if (imageView == null) {
            throw IllegalArgumentException("argument error")
        }
        Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).error(R.mipmap.toux2).centerCrop().transform(GlideRoundTransformUtil(context)).into(imageView)
    }

}
