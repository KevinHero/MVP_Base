package com.apanda.base.Utils

//
//  Created by XuGuobiao  2012-8-25
//  Copyright 2012年 ever. All rights reserved
//
import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AccelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.AnimationSet
import android.view.animation.AnticipateInterpolator
import android.view.animation.AnticipateOvershootInterpolator
import android.view.animation.BounceInterpolator
import android.view.animation.DecelerateInterpolator
import android.view.animation.LinearInterpolator
import android.view.animation.OvershootInterpolator
import android.view.animation.RotateAnimation
import android.view.animation.ScaleAnimation
import android.view.animation.TranslateAnimation

class AnimationController {
    val rela1 = Animation.RELATIVE_TO_SELF
    val rela2 = Animation.RELATIVE_TO_PARENT

    val Default = -1
    val Linear = 0
    val Accelerate = 1
    val Decelerate = 2
    val AccelerateDecelerate = 3
    val Bounce = 4
    val Overshoot = 5
    val Anticipate = 6
    val AnticipateOvershoot = 7


    private inner class MyAnimationListener : AnimationListener {
        private var view: View? = null
        private var visiabilityOnEnd: Int = 0

        constructor(view: View) {
            this.view = view
            visiabilityOnEnd = View.GONE
        }

        constructor(view: View, visiabilityOnEnd: Int) {
            this.view = view
            this.visiabilityOnEnd = visiabilityOnEnd
        }

        override fun onAnimationStart(animation: Animation) {
            // this.view.setVisibility(View.VISIBLE);
        }

        override fun onAnimationEnd(animation: Animation) {
            this.view!!.visibility = visiabilityOnEnd
        }

        override fun onAnimationRepeat(animation: Animation) {
        }
    }

    private fun setEffect(animation: Animation, interpolatorType: Int, durationMillis: Long, delayMillis: Long) {
        when (interpolatorType) {
            0 -> animation.interpolator = LinearInterpolator()
            1 -> animation.interpolator = AccelerateInterpolator()
            2 -> animation.interpolator = DecelerateInterpolator()
            3 -> animation.interpolator = AccelerateDecelerateInterpolator()
            4 -> animation.interpolator = BounceInterpolator()
            5 -> animation.interpolator = OvershootInterpolator()
            6 -> animation.interpolator = AnticipateInterpolator()
            7 -> animation.interpolator = AnticipateOvershootInterpolator()
            else -> {
            }
        }
        animation.duration = durationMillis
        animation.startOffset = delayMillis
    }

    private fun baseIn(view: View, animation: Animation, durationMillis: Long, delayMillis: Long) {
        setEffect(animation, Default, durationMillis, delayMillis)
        view.visibility = View.VISIBLE
        view.startAnimation(animation)
    }

    private fun baseOut(view: View, animation: Animation, durationMillis: Long, delayMillis: Long, visiblityOnEnd: Int) {
        setEffect(animation, Default, durationMillis, delayMillis)
        animation.setAnimationListener(MyAnimationListener(view, visiblityOnEnd))
        view.startAnimation(animation)
    }

    private fun baseOut(view: View, animation: Animation, durationMillis: Long, delayMillis: Long) {
        setEffect(animation, Default, durationMillis, delayMillis)
        animation.setAnimationListener(MyAnimationListener(view))
        view.startAnimation(animation)
    }

    fun show(view: View) {
        view.visibility = View.VISIBLE
    }

    fun hide(view: View) {
        view.visibility = View.GONE
    }

    fun transparent(view: View) {
        view.visibility = View.INVISIBLE
    }

    fun fadeIn(view: View, durationMillis: Long, delayMillis: Long) {
        val animation = AlphaAnimation(0f, 1f)
        baseIn(view, animation, durationMillis, delayMillis)
    }

    fun fadeOut(view: View, durationMillis: Long, delayMillis: Long) {
        val animation = AlphaAnimation(1f, 0f)
        baseOut(view, animation, durationMillis, delayMillis)
    }

    fun slideIn(view: View, durationMillis: Long, delayMillis: Long) {
        val animation = TranslateAnimation(rela2, 1f, rela2, 0f, rela2, 0f, rela2, 0f)
        baseIn(view, animation, durationMillis, delayMillis)
    }

    fun slideOut(view: View, durationMillis: Long, delayMillis: Long) {
        val animation = TranslateAnimation(rela2, 0f, rela2, -1f, rela2, 0f, rela2, 0f)
        baseOut(view, animation, durationMillis, delayMillis)
    }

    fun slideUp(view: View, durationMillis: Long, delayMillis: Long) {
        val animation = TranslateAnimation(rela2, 0f, rela2, 0f, rela2, 0f, rela2, 1f)
        baseOut(view, animation, durationMillis, delayMillis)
    }

    fun slideDown(view: View, durationMillis: Long, delayMillis: Long) {
        val animation = TranslateAnimation(rela2, 0f, rela2, 0f, rela2, 1f, rela2, 0f)
        baseOut(view, animation, durationMillis, delayMillis)
    }

    fun scaleIn(view: View, durationMillis: Long, delayMillis: Long) {
        val animation = ScaleAnimation(0f, 1f, 0f, 1f, rela2, 0.5f, rela2, 0.5f)
        baseIn(view, animation, durationMillis, delayMillis)
    }

    fun scaleIn2(view: View, durationMillis: Long, delayMillis: Long) {
        val animation = ScaleAnimation(2f, 0.9f, 2f, 0.9f, rela1, 0.5f, rela1, 0.5f)
        baseIn(view, animation, durationMillis, delayMillis)
    }

    fun scaleOut(view: View, durationMillis: Long, delayMillis: Long) {
        val animation = ScaleAnimation(1f, 0f, 1f, 0f, rela2, 0.5f, rela2, 0.5f)
        baseOut(view, animation, durationMillis, delayMillis)
    }

    fun rotateIn(view: View, durationMillis: Long, delayMillis: Long) {
        val animation = RotateAnimation(-90f, 0f, rela1, 0f, rela1, 1f)
        baseIn(view, animation, durationMillis, delayMillis)
    }

    fun rotateOut(view: View, durationMillis: Long, delayMillis: Long) {
        val animation = RotateAnimation(0f, 90f, rela1, 0f, rela1, 1f)
        baseOut(view, animation, durationMillis, delayMillis)
    }

    fun scaleRotateIn(view: View, durationMillis: Long, delayMillis: Long) {
        val animation1 = ScaleAnimation(0f, 1f, 0f, 1f, rela1, 0.5f, rela1, 0.5f)
        val animation2 = RotateAnimation(0f, 360f, rela1, 0.5f, rela1, 0.5f)
        val animation = AnimationSet(false)
        animation.addAnimation(animation1)
        animation.addAnimation(animation2)
        baseIn(view, animation, durationMillis, delayMillis)
    }

    fun scaleRotateOut(view: View, durationMillis: Long, delayMillis: Long) {
        val animation1 = ScaleAnimation(1f, 0f, 1f, 0f, rela1, 0.5f, rela1, 0.5f)
        val animation2 = RotateAnimation(0f, 360f, rela1, 0.5f, rela1, 0.5f)
        val animation = AnimationSet(false)
        animation.addAnimation(animation1)
        animation.addAnimation(animation2)
        baseOut(view, animation, durationMillis, delayMillis)
    }

    fun slideFadeIn(view: View, durationMillis: Long, delayMillis: Long) {
        val animation1 = TranslateAnimation(rela2, 1f, rela2, 0f, rela2, 0f, rela2, 0f)
        val animation2 = AlphaAnimation(0f, 1f)
        val animation = AnimationSet(false)
        animation.addAnimation(animation1)
        animation.addAnimation(animation2)
        baseIn(view, animation, durationMillis, delayMillis)
    }

    fun slideFadeOut(view: View, durationMillis: Long, delayMillis: Long) {
        val animation1 = TranslateAnimation(rela2, 0f, rela2, -1f, rela2, 0f, rela2, 0f)
        val animation2 = AlphaAnimation(1f, 0f)
        val animation = AnimationSet(false)
        animation.addAnimation(animation1)
        animation.addAnimation(animation2)
        baseOut(view, animation, durationMillis, delayMillis)
    }

    fun slideFadeIn_up(view: View, durationMillis: Long, delayMillis: Long) {
        val animation1 = TranslateAnimation(rela2, 0f, rela2, 0f, rela2, 1f, rela2, 0f)
        val animation2 = AlphaAnimation(0f, 1f)
        val animation = AnimationSet(false)
        animation.addAnimation(animation1)
        animation.addAnimation(animation2)
        baseIn(view, animation, durationMillis, delayMillis)
    }

    fun slideFadeIn_up(view: View, durationMillis: Long, delayMillis: Long, rela: Int) {
        val animation1 = TranslateAnimation(rela, 0f, rela, 0f, rela, 1f, rela, 0f)
        val animation2 = AlphaAnimation(0f, 1f)
        val animation = AnimationSet(false)
        animation.addAnimation(animation1)
        animation.addAnimation(animation2)
        baseIn(view, animation, durationMillis, delayMillis)
    }

    fun slideFadeOut_down(view: View, durationMillis: Long, delayMillis: Long) {
        val animation1 = TranslateAnimation(rela2, 0f, rela2, 0f, rela2, 0f, rela2, 1f)
        val animation2 = AlphaAnimation(1f, 0f)
        val animation = AnimationSet(false)
        animation.addAnimation(animation1)
        animation.addAnimation(animation2)
        baseOut(view, animation, durationMillis, delayMillis)
    }

    fun slideFadeOut_down(view: View, durationMillis: Long, delayMillis: Long, visibilityOnEnd: Int) {
        val animation1 = TranslateAnimation(rela2, 0f, rela2, 0f, rela2, 0f, rela2, 1f)
        val animation2 = AlphaAnimation(1f, 0f)
        val animation = AnimationSet(false)
        animation.addAnimation(animation1)
        animation.addAnimation(animation2)
        baseOut(view, animation, durationMillis, delayMillis, visibilityOnEnd)
    }

    fun slideFadeOut_down(view: View, durationMillis: Long, delayMillis: Long, visibilityOnEnd: Int, rela: Int) {
        val animation1 = TranslateAnimation(rela, 0f, rela, 0f, rela, 0f, rela, 1f)
        val animation2 = AlphaAnimation(1f, 0f)
        val animation = AnimationSet(false)
        animation.addAnimation(animation1)
        animation.addAnimation(animation2)
        baseOut(view, animation, durationMillis, delayMillis, visibilityOnEnd)
    }


    fun slideLeft(view: View, durationMillis: Long, delayMillis: Long) {
        val animation1 = TranslateAnimation(rela2, 1f, rela2, 0f, rela2, 0f, rela2, 0f)
        val animation2 = AlphaAnimation(0f, 1f)
        val animation = AnimationSet(false)
        animation.addAnimation(animation1)
        animation.addAnimation(animation2)
        baseIn(view, animation, durationMillis, delayMillis)
    }

    companion object {

        /**
         * 使用属性动画把空间view移动到合适的位置
         * 已弃用，考虑删除
         * @param view
         * *
         * @param suitablePosition
         */
        fun translateOfTouch(view: View, suitablePosition: Int) {
            val duration = 300
            val animator = ObjectAnimator.ofFloat(view, "TranslationX", suitablePosition - view.layoutParams.width / 2).setDuration(duration.toLong())
            animator.setInterpolator(AccelerateDecelerateInterpolator())
            animator.start()
        }

        /**
         * 控件跟随手指
         * 已弃用，考虑删除
         * @param view
         * *
         * @param startPosition
         * *
         * @param moveX
         */
        fun followFinger(view: View, startPosition: Float, moveX: Float) {
            //        ObjectAnimator.ofFloat(view,"translationX",startPosition,startPosition+moveX-view.getLayoutParams().width/2).setDuration(1).start();
            ObjectAnimator.ofFloat(view, "translationX", startPosition, startPosition + moveX).setDuration(1).start()
        }

        /**
         * 控件纵向移动
         * @param v
         */
        fun translateY(v: View, startY: Int, endY: Int) {
            val duration = 200
            val animator = ObjectAnimator.ofFloat(v, "translationY", startY, endY)
            animator.setDuration(duration.toLong())
            animator.setInterpolator(AccelerateDecelerateInterpolator())
            animator.start()
        }
    }
}// LinearInterpolator,AccelerateInterpolator,DecelerateInterpolator,AccelerateDecelerateInterpolator,
// BounceInterpolator,OvershootInterpolator,AnticipateInterpolator,AnticipateOvershootInterpolator
