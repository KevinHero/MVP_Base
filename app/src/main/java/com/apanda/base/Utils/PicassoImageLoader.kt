package com.apanda.base.Utils

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.widget.ImageView

import com.apanda.base.R
import com.lzy.imagepicker.bean.ImageItem
import com.lzy.imagepicker.loader.ImageLoader
import com.lzy.ninegrid.NineGridView
import com.squareup.picasso.Picasso

import java.io.File

/**
 * ================================================
 * 作    者：jeasonlzy（廖子尧）Github地址：https://github.com/jeasonlzy0216
 * 版    本：1.0
 * 创建日期：16/9/1
 * 描    述：
 * 修订历史：
 * ================================================
 */
class PicassoImageLoader : ImageLoader, NineGridView.ImageLoader {


    fun displayImage(activity: Activity, imageView: ImageView, path: String) {
        Picasso.with(activity)                           //配置上下文
                .load(Uri.fromFile(File(path)))      //设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
                .error(R.mipmap.default_image)           //设置错误图片
                .placeholder(R.mipmap.default_image)     //设置占位图片
                .into(imageView)
    }

    fun displayImage(activity: Activity, imageView: ImageView, _imageItem: ImageItem) {
        Picasso.with(activity)                           //配置上下文
                .load(_imageItem.path)      //设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
                .error(R.mipmap.default_image)           //设置错误图片
                .placeholder(R.mipmap.default_image)     //设置占位图片
                .into(imageView)
    }

    override fun displayImage(activity: Activity, path: String, imageView: ImageView, width: Int, height: Int) {

    }

    override fun clearMemoryCache() {
    }

    override fun onDisplayImage(context: Context, imageView: ImageView, url: String) {
        Picasso.with(context).load(url)//
                .placeholder(R.mipmap.default_image)//
                .error(R.mipmap.default_image)//
                .into(imageView)
    }

    override fun getCacheImage(url: String): Bitmap? {
        return null
    }
}
