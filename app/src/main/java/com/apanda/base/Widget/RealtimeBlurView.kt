package com.apanda.base.Widget

/**
 * Created by Android on 2016/10/13.
 */


import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.res.TypedArray
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RSRuntimeException
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.ViewTreeObserver

import com.apanda.base.R


/**
 * A realtime blurring overlay (like iOS UIVisualEffectView). Just put it above
 * the view you want to blur and it doesn't have to be in the same ViewGroup
 *
 *  * realtimeBlurRadius (10dp)
 *  * realtimeDownsampleFactor (4)
 *  * realtimeOverlayColor (#aaffffff)
 *
 */
class RealtimeBlurView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var mDownsampleFactor: Float = 0.toFloat() // default 4
    private var mOverlayColor: Int = 0 // default #aaffffff
    private var mBlurRadius: Float = 0.toFloat() // default 10dp (0 < r <= 25)

    private var mDirty: Boolean = false
    private var mBitmapToBlur: Bitmap? = null
    private var mBlurredBitmap: Bitmap? = null
    private var mBlurringCanvas: Canvas? = null
    private var mRenderScript: RenderScript? = null
    private var mBlurScript: ScriptIntrinsicBlur? = null
    private var mBlurInput: Allocation? = null
    private var mBlurOutput: Allocation? = null
    private var mIsRendering: Boolean = false
    private val mRectSrc = Rect()
    private val mRectDst = Rect()

    init {

        val a = context.obtainStyledAttributes(attrs, R.styleable.RealtimeBlurView)
        mBlurRadius = a.getDimension(R.styleable.RealtimeBlurView_realtimeBlurRadius,
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10f, context.resources.displayMetrics))
        mDownsampleFactor = a.getFloat(R.styleable.RealtimeBlurView_realtimeDownsampleFactor, 4f)
        mOverlayColor = a.getColor(R.styleable.RealtimeBlurView_realtimeOverlayColor, 0xAAFFFFFF.toInt())
        a.recycle()
    }

    fun setBlurRadius(radius: Float) {
        if (mBlurRadius != radius) {
            mBlurRadius = radius
            mDirty = true
            invalidate()
        }
    }

    fun setDownsampleFactor(factor: Float) {
        if (factor <= 0) {
            throw IllegalArgumentException("Downsample factor must be greater than 0.")
        }

        if (mDownsampleFactor != factor) {
            mDownsampleFactor = factor
            mDirty = true // may also change blur radius
            releaseBitmap()
            invalidate()
        }
    }

    fun setOverlayColor(color: Int) {
        if (mOverlayColor != color) {
            mOverlayColor = color
            invalidate()
        }
    }

    private fun releaseBitmap() {
        if (mBlurInput != null) {
            mBlurInput!!.destroy()
            mBlurInput = null
        }
        if (mBlurOutput != null) {
            mBlurOutput!!.destroy()
            mBlurOutput = null
        }
        if (mBitmapToBlur != null) {
            mBitmapToBlur!!.recycle()
            mBitmapToBlur = null
        }
        if (mBlurredBitmap != null) {
            mBlurredBitmap!!.recycle()
            mBlurredBitmap = null
        }
    }

    private fun releaseScript() {
        if (mRenderScript != null) {
            mRenderScript!!.destroy()
            mRenderScript = null
        }
        if (mBlurScript != null) {
            mBlurScript!!.destroy()
            mBlurScript = null
        }
    }

    protected fun release() {
        releaseBitmap()
        releaseScript()
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    protected fun prepare(): Boolean {
        if (mBlurRadius == 0f) {
            release()
            return false
        }

        var downsampleFactor = mDownsampleFactor

        if (mDirty || mRenderScript == null) {
            if (mRenderScript == null) {
                try {
                    mRenderScript = RenderScript.create(context)
                    mBlurScript = ScriptIntrinsicBlur.create(mRenderScript, Element.U8_4(mRenderScript))
                } catch (e: RSRuntimeException) {
                    if (e.message != null && e.message.startsWith("Error loading RS jni library: java.lang.UnsatisfiedLinkError:")) {
                        throw RuntimeException("Error loading RS jni library, Upgrade buildToolsVersion=\"23.0.3\" or higher may solve this issue")
                    } else {
                        throw e
                    }
                }

            }

            mDirty = false
            var radius = mBlurRadius / downsampleFactor
            if (radius > 25) {
                downsampleFactor = downsampleFactor * radius / 25
                radius = 25f
            }
            mBlurScript!!.setRadius(radius)
        }

        val width = width
        val height = height

        val scaledWidth = (width / downsampleFactor).toInt()
        val scaledHeight = (height / downsampleFactor).toInt()

        if (mBlurringCanvas == null || mBlurredBitmap == null
                || mBlurredBitmap!!.width != scaledWidth
                || mBlurredBitmap!!.height != scaledHeight) {
            releaseBitmap()

            mBitmapToBlur = Bitmap.createBitmap(scaledWidth, scaledHeight, Bitmap.Config.ARGB_8888)
            if (mBitmapToBlur == null) {
                return false
            }

            mBlurredBitmap = Bitmap.createBitmap(scaledWidth, scaledHeight, Bitmap.Config.ARGB_8888)
            if (mBlurredBitmap == null) {
                return false
            }

            mBlurringCanvas = Canvas(mBitmapToBlur!!)
            mBlurInput = Allocation.createFromBitmap(mRenderScript, mBitmapToBlur,
                    Allocation.MipmapControl.MIPMAP_NONE, Allocation.USAGE_SCRIPT)
            mBlurOutput = Allocation.createTyped(mRenderScript, mBlurInput!!.type)
        }
        return true
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    protected fun blur() {
        mBlurInput!!.copyFrom(mBitmapToBlur)
        mBlurScript!!.setInput(mBlurInput)
        mBlurScript!!.forEach(mBlurOutput)
        mBlurOutput!!.copyTo(mBlurredBitmap)
    }

    private val preDrawListener = ViewTreeObserver.OnPreDrawListener {
        val locations = IntArray(2)
        if (isShown && prepare()) {
            var a: Activity? = null
            var ctx = getContext()
            while (true) {
                if (ctx is Activity) {
                    a = ctx
                    break
                } else if (ctx is ContextWrapper) {
                    ctx = ctx.baseContext
                } else {
                    break
                }
            }
            if (a == null) {
                // Not in a activity
                return@OnPreDrawListener true
            }

            val decor = a.window.decorView
            decor.getLocationInWindow(locations)
            var x = -locations[0]
            var y = -locations[1]

            getLocationInWindow(locations)
            x += locations[0]
            y += locations[1]

            if (decor.background is ColorDrawable) {
                mBitmapToBlur!!.eraseColor((decor.background as ColorDrawable).color)
            } else {
                mBitmapToBlur!!.eraseColor(Color.TRANSPARENT)
            }

            val rc = mBlurringCanvas!!.save()
            mIsRendering = true
            RENDERING_COUNT++
            try {
                mBlurringCanvas!!.scale(1.f * mBlurredBitmap!!.width / width, 1.f * mBlurredBitmap!!.height / height)
                mBlurringCanvas!!.translate((-x).toFloat(), (-y).toFloat())
                decor.draw(mBlurringCanvas)
            } catch (e: StopException) {
            } finally {
                mIsRendering = false
                RENDERING_COUNT--
                mBlurringCanvas!!.restoreToCount(rc)
            }

            blur()
        }

        true
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        viewTreeObserver.addOnPreDrawListener(preDrawListener)
    }

    override fun onDetachedFromWindow() {
        viewTreeObserver.removeOnPreDrawListener(preDrawListener)
        release()
        super.onDetachedFromWindow()
    }

    override fun draw(canvas: Canvas) {
        if (mIsRendering) {
            // Quit here, don't draw views above me
            throw STOP_EXCEPTION
        } else if (RENDERING_COUNT > 0) {
            // Doesn't support blurview overlap on another blurview
        } else {
            super.draw(canvas)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawBlurredBitmap(canvas, mBlurredBitmap, mOverlayColor)
    }

    /**
     * Custom draw the blurred bitmap and color to define your own shape

     * @param canvas
     * *
     * @param blurredBitmap
     * *
     * @param overlayColor
     */
    protected fun drawBlurredBitmap(canvas: Canvas, blurredBitmap: Bitmap?, overlayColor: Int) {
        if (blurredBitmap != null) {
            mRectSrc.right = blurredBitmap.width
            mRectSrc.bottom = blurredBitmap.height
            mRectDst.right = width
            mRectDst.bottom = height
            canvas.drawBitmap(blurredBitmap, mRectSrc, mRectDst, null)
        }
        canvas.drawColor(overlayColor)
    }

    private class StopException : RuntimeException()

    companion object {
        private var RENDERING_COUNT: Int = 0

        private val STOP_EXCEPTION = StopException()

        init {
            try {
                RealtimeBlurView::class.java.classLoader.loadClass("android.support.v8.renderscript.RenderScript")
            } catch (e: ClassNotFoundException) {
                throw RuntimeException("RenderScript support not enabled. Add \"android { defaultConfig { renderscriptSupportModeEnabled true }}\" in your build.gradle")
            }

        }
    }
}