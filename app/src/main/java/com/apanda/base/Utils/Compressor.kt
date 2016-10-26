package com.apanda.base.Utils

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri

import java.io.File

import rx.Observable
import rx.functions.Func0

/**
 * 压缩图片类
 */

//**********************************************************使用方法******************************************
//方法一：
//File compressedImage = new Compressor.Builder(this)
//        .setMaxWidth(640)
//        .setMaxHeight(480)
//        .setQuality(75)
//        .setCompressFormat(Bitmap.CompressFormat.JPEG)
//        .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
//        Environment.DIRECTORY_PICTURES).getAbsolutePath())
//        .build()
//        .compressToFile(actualImage);


//方法二

//Compressor.getDefault(this)
//        .compressToFileAsObservable(actualImage)
//        .subscribeOn(Schedulers.io())
//        .observeOn(AndroidSchedulers.mainThread())
//        .subscribe(new Action1<File>() {
//@Override
//public void call(File file) {
//        compressedImage = file;
//        setCompressedImage();
//        }
//        }, new Action1<Throwable>() {
//@Override
//public void call(Throwable throwable) {
//        showError(throwable.getMessage());
//        }
//        });

class Compressor private constructor(private val context: Context) {
    //max width and height values of the compressed image is taken as 612x816
    private var maxWidth = 612.0f
    private var maxHeight = 816.0f
    private var compressFormat: Bitmap.CompressFormat = Bitmap.CompressFormat.JPEG
    private var quality = 80
    private var destinationDirectoryPath: String? = null

    init {
        destinationDirectoryPath = context.cacheDir.path + File.pathSeparator + FileUtil.FILES_PATH
    }

    fun compressToFile(file: File): File {
        return ImageUtil.compressImage(context, Uri.fromFile(file), maxWidth, maxHeight, compressFormat, quality, destinationDirectoryPath)
    }

    fun compressToBitmap(file: File): Bitmap {
        return ImageUtil.getScaledBitmap(context, Uri.fromFile(file), maxWidth, maxHeight)
    }

    fun compressToFileAsObservable(file: File): Observable<File> {
        return Observable.defer { Observable.just(compressToFile(file)) }
    }

    fun compressToBitmapAsObservable(file: File): Observable<Bitmap> {
        return Observable.defer { Observable.just(compressToBitmap(file)) }
    }

    class Builder(context: Context) {
        private val compressor: Compressor

        init {
            compressor = Compressor(context)
        }

        fun setMaxWidth(maxWidth: Float): Builder {
            compressor.maxWidth = maxWidth
            return this
        }

        fun setMaxHeight(maxHeight: Float): Builder {
            compressor.maxHeight = maxHeight
            return this
        }

        fun setCompressFormat(compressFormat: Bitmap.CompressFormat): Builder {
            compressor.compressFormat = compressFormat
            return this
        }

        fun setQuality(quality: Int): Builder {
            compressor.quality = quality
            return this
        }

        fun setDestinationDirectoryPath(destinationDirectoryPath: String): Builder {
            compressor.destinationDirectoryPath = destinationDirectoryPath
            return this
        }

        fun build(): Compressor {
            return compressor
        }
    }

    companion object {
        @Volatile private var INSTANCE: Compressor? = null

        fun getDefault(context: Context): Compressor {
            if (INSTANCE == null) {
                synchronized(Compressor::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Compressor(context)
                    }
                }
            }
            return INSTANCE
        }
    }
}
