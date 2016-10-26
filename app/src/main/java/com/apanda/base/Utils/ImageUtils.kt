package com.apanda.base.Utils

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Shader
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable

import java.io.ByteArrayOutputStream

/**
 *
 * author: Blankj
 * blog  : http://blankj.com
 * time  : 2016/8/12
 * desc  : 图片相关工具类
 *
 */
class ImageUtils private constructor() {

    init {
        throw UnsupportedOperationException("u can't fuck me...")
    }

    companion object {

        /**
         * convert Bitmap to byte array
         */
        fun bitmapToBytes(b: Bitmap?): ByteArray? {
            if (b == null) return null
            val baos = ByteArrayOutputStream()
            b.compress(Bitmap.CompressFormat.PNG, 100, baos)
            return baos.toByteArray()
        }

        fun bytesToBitmap(b: ByteArray?): Bitmap? {
            return if (b == null || b.size == 0) null else BitmapFactory.decodeByteArray(b, 0, b.size)
        }

        /**
         * convert Drawable to Bitmap
         */
        fun drawableToBitmap(drawable: Drawable?): Bitmap? {
            return if (drawable == null) null else (drawable as BitmapDrawable).bitmap
        }

        /**
         * convert Bitmap to Drawable
         */
        fun bitmapToDrawable(resources: Resources, bitmap: Bitmap?): Drawable? {
            return if (bitmap == null) null else BitmapDrawable(resources, bitmap)
        }


        fun toRoundCorner(bitmap: Bitmap): Bitmap {
            val height = bitmap.height
            val width = bitmap.height
            val output = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

            val canvas = Canvas(output)

            val paint = Paint()
            val rect = Rect(0, 0, width, height)

            paint.isAntiAlias = true
            canvas.drawARGB(0, 0, 0, 0)
            //paint.setColor(0xff424242);
            paint.color = Color.TRANSPARENT
            canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), (width / 2).toFloat(), paint)
            paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
            canvas.drawBitmap(bitmap, rect, rect, paint)
            return output
        }

        fun getRoundedCornerBitmap(bitmap: Bitmap, roundPx: Float): Bitmap {
            val output = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(output)
            val color = 0xff424242.toInt()
            val paint = Paint()
            val rect = Rect(0, 0, bitmap.width, bitmap.height)
            val rectF = RectF(rect)
            paint.isAntiAlias = true
            canvas.drawARGB(0, 0, 0, 0)
            paint.color = color
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint)
            paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
            canvas.drawBitmap(bitmap, rect, rect, paint)
            return output
        }


        fun createReflectionImageWithOrigin(bitmap: Bitmap): Bitmap {
            val reflectionGap = 4
            val width = bitmap.width
            val height = bitmap.height
            val matrix = Matrix()
            matrix.preScale(1f, -1f)
            val reflectionImage = Bitmap.createBitmap(bitmap,
                    0, height / 2, width, height / 2, matrix, false)
            val bitmapWithReflection = Bitmap.createBitmap(width, height + height / 2,
                    Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmapWithReflection)
            canvas.drawBitmap(bitmap, 0f, 0f, null)
            canvas.drawRect(0f, height.toFloat(), width.toFloat(), (height + reflectionGap).toFloat(),
                    Paint())
            canvas.drawBitmap(reflectionImage, 0f, (height + reflectionGap).toFloat(), null)
            val paint = Paint()
            val shader = LinearGradient(0f,
                    bitmap.height.toFloat(), 0f, (bitmapWithReflection.height + reflectionGap).toFloat(), 0x70ffffff, 0x00ffffff, Shader.TileMode.CLAMP)
            paint.shader = shader
            // Set the Transfer mode to be porter duff and destination in
            paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
            // Draw a rectangle using the paint with our linear gradient
            canvas.drawRect(0f, height.toFloat(), width.toFloat(), (bitmapWithReflection.height + reflectionGap).toFloat(), paint)
            return bitmapWithReflection
        }
    }
}
