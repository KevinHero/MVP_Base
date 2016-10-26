package com.apanda.base.Utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.media.ExifInterface
import android.net.Uri

import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException

/**
 * 图像util
 */
internal object ImageUtil {

    /**
     * 获取压缩后bitmap
     * @param context
     * *
     * @param imageUri
     * *
     * @param maxWidth
     * *
     * @param maxHeight
     * *
     * @return
     */
    fun getScaledBitmap(context: Context, imageUri: Uri, maxWidth: Float, maxHeight: Float): Bitmap {
        val filePath = FileUtil.getRealPathFromURI(context, imageUri)
        var scaledBitmap: Bitmap? = null

        val options = BitmapFactory.Options()

        //by setting this field as true, the actual bitmap pixels are not loaded in the memory. Just the bounds are loaded. If
        //you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true
        var bmp = BitmapFactory.decodeFile(filePath, options)

        var actualHeight = options.outHeight
        var actualWidth = options.outWidth

        var imgRatio = actualWidth.toFloat() / actualHeight
        val maxRatio = maxWidth / maxHeight

        //width and height values are set maintaining the aspect ratio of the image
        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight
                actualWidth = (imgRatio * actualWidth).toInt()
                actualHeight = maxHeight.toInt()
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth
                actualHeight = (imgRatio * actualHeight).toInt()
                actualWidth = maxWidth.toInt()
            } else {
                actualHeight = maxHeight.toInt()
                actualWidth = maxWidth.toInt()
            }
        }

        //setting inSampleSize value allows to load a scaled down version of the original image
        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight)

        //inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false

        //this options allow android to claim the bitmap memory if it runs low on memory
        options.inPurgeable = true
        options.inInputShareable = true
        options.inTempStorage = ByteArray(16 * 1024)

        try {
            //load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options)
        } catch (exception: OutOfMemoryError) {
            exception.printStackTrace()
        }

        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888)
        } catch (exception: OutOfMemoryError) {
            exception.printStackTrace()
        }

        val ratioX = actualWidth / options.outWidth.toFloat()
        val ratioY = actualHeight / options.outHeight.toFloat()
        val middleX = actualWidth / 2.0f
        val middleY = actualHeight / 2.0f

        val scaleMatrix = Matrix()
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY)

        val canvas = Canvas(scaledBitmap!!)
        canvas.matrix = scaleMatrix
        canvas.drawBitmap(bmp, middleX - bmp.width / 2, middleY - bmp.height / 2, Paint(Paint.FILTER_BITMAP_FLAG))

        //check the rotation of the image and display it properly
        val exif: ExifInterface
        try {
            exif = ExifInterface(filePath)
            val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0)
            val matrix = Matrix()
            if (orientation == 6) {
                matrix.postRotate(90f)
            } else if (orientation == 3) {
                matrix.postRotate(180f)
            } else if (orientation == 8) {
                matrix.postRotate(270f)
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.width, scaledBitmap.height,
                    matrix, true)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return scaledBitmap
    }

    /**
     * 压缩图片
     * @param context
     * *
     * @param imageUri
     * *
     * @param maxWidth
     * *
     * @param maxHeight
     * *
     * @param compressFormat
     * *
     * @param quality
     * *
     * @param parentPath
     * *
     * @return
     */
    fun compressImage(context: Context, imageUri: Uri, maxWidth: Float, maxHeight: Float, compressFormat: Bitmap.CompressFormat, quality: Int, parentPath: String): File {
        var out: FileOutputStream? = null
        val filename = generateFilePath(context, parentPath, imageUri, compressFormat.name.toLowerCase())
        try {
            out = FileOutputStream(filename)

            //write the compressed bitmap at the destination specified by filename.
            ImageUtil.getScaledBitmap(context, imageUri, maxWidth, maxHeight).compress(compressFormat, quality, out)

        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } finally {
            try {
                if (out != null) {
                    out.close()
                }
            } catch (ignored: IOException) {
            }

        }

        return File(filename)
    }

    private fun generateFilePath(context: Context, parentPath: String, uri: Uri, extension: String): String {
        val file = File(parentPath)
        if (!file.exists()) {
            file.mkdirs()
        }
        return file.absolutePath + File.separator + FileUtil.splitFileName(FileUtil.getFileName(context, uri))[0] + "." + extension
    }

    private fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {
            val heightRatio = Math.round(height.toFloat() / reqHeight.toFloat())
            val widthRatio = Math.round(width.toFloat() / reqWidth.toFloat())
            inSampleSize = if (heightRatio < widthRatio) heightRatio else widthRatio
        }

        val totalPixels = (width * height).toFloat()
        val totalReqPixelsCap = reqWidth * reqHeight * 2.toFloat()

        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++
        }

        return inSampleSize
    }
}
