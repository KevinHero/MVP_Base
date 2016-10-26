package com.apanda.base.Module.Gank.activity

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.os.Message
import android.view.animation.AccelerateInterpolator
import android.widget.TextView
import butterknife.Bind
import com.apanda.base.R
import com.apanda.base.base.BaseActivity
import com.apanda.base.base.BaseModel
import com.apanda.base.base.BasePresenter

class StartupActivity : BaseActivity<BasePresenter<*, *>, BaseModel>(){
    @Bind(R.id.iv_logo)
    internal var _IvLogo: TextView? = null

    /**
     * 是否支持滑动返回

     * @return
     */
    override fun supportSlideBack(): Boolean {
        return false
    }


    override fun refresh(msg: Message) {

    }

    override val layoutId: Int
        get() = R.layout.activity_startup

    override fun initPresenter() {

    }

    override fun initView() {


        val alpha = PropertyValuesHolder.ofFloat("alpha", 0.3f, 1f)
        val scaleX = PropertyValuesHolder.ofFloat("scaleX", 0.3f, 1f)
        val scaleY = PropertyValuesHolder.ofFloat("scaleY", 0.3f, 1f)
        val objectAnimator1 = ObjectAnimator.ofPropertyValuesHolder(_IvLogo, alpha, scaleX, scaleY)
        val objectAnimator2 = ObjectAnimator.ofPropertyValuesHolder(_IvLogo, alpha, scaleX, scaleY)

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(objectAnimator1, objectAnimator2)
        animatorSet.interpolator = AccelerateInterpolator()
        animatorSet.duration = 2000
        animatorSet.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animator: Animator) {

            }

            override fun onAnimationEnd(animator: Animator) {
                startActivity(HomeActivity::class.java)
                finish()
            }

            override fun onAnimationCancel(animator: Animator) {

            }

            override fun onAnimationRepeat(animator: Animator) {

            }
        })
        animatorSet.start()


    }

}
