package com.apanda.base.Utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.apanda.base.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lzy.imagepicker.loader.ImageLoader;
import com.lzy.ninegrid.NineGridView;

import java.io.File;

/**
 * ================================================
 * 作    者：jeasonlzy（廖子尧）Github地址：https://github.com/jeasonlzy0216
 * 版    本：1.0
 * 创建日期：16/9/5
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class GlideImageLoader implements ImageLoader, NineGridView.ImageLoader {

    private static GlideImageLoader _singleInstance = null;

    private GlideImageLoader(){
    }

    public static GlideImageLoader getInstance() {
        synchronized (GlideImageLoader.class) {
            if (_singleInstance == null) {
                _singleInstance = new GlideImageLoader();
            }
        }
        return _singleInstance;
    }

    @Override
    public void onDisplayImage(Context context, ImageView imageView, String url) {
        Glide.with(context).load(url)//
                .placeholder(R.mipmap.default_image)//
                .error(R.mipmap.default_image)//
                .diskCacheStrategy(DiskCacheStrategy.ALL)//
                .into(imageView);
    }


    public  void onDisplayImage(Context context, ImageView imageView, Bitmap url) {
        Glide.with(context).load(url)//
                .placeholder(R.mipmap.default_image)//
                .error(R.mipmap.default_image)//
                .diskCacheStrategy(DiskCacheStrategy.ALL)//
                .into(imageView);
    }

    public  void onDisplayImage(Context context, ImageView imageView, int id) {
        Glide.with(context).load(id)//
                .placeholder(R.mipmap.default_image)//
                .error(R.mipmap.default_image)//
                .diskCacheStrategy(DiskCacheStrategy.ALL)//
                .into(imageView);
    }

    @Override
    public Bitmap getCacheImage(String url) {
        return null;
    }

    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
        Glide.with(activity).load(new File(path))//
                .placeholder(R.mipmap.default_image)//
                .error(R.mipmap.default_image)//
                .diskCacheStrategy(DiskCacheStrategy.ALL)//
                .into(imageView);
    }

    public static void displayRound(Context context,ImageView imageView, String url) {
        if (imageView == null) {
            throw new IllegalArgumentException("argument error");
        }
        Glide.with(context).load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .error(R.mipmap.toux2)
                .centerCrop().transform(new GlideRoundTransformUtil(context)).into(imageView);
    }


    @Override
    public void clearMemoryCache() {
    }
}
