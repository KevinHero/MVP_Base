package com.apanda.base.Utils.SwipeBackUtils

import android.app.Activity
import android.app.Application
import android.os.Bundle

import java.util.Stack

/**
 * Created by Android on 2016/10/12.
 */

class ActivityLifecycleHelper : Application.ActivityLifecycleCallbacks {

    init {
        activityStack = Stack<Activity>()
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle) {
        addActivity(activity)
    }

    override fun onActivityStarted(activity: Activity) {

    }

    override fun onActivityResumed(activity: Activity) {

    }

    override fun onActivityPaused(activity: Activity) {

    }

    override fun onActivityStopped(activity: Activity) {
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

    }

    override fun onActivityDestroyed(activity: Activity) {
        removeActivity(activity)
    }

    /**
     * 添加Activity到堆栈
     */
    fun addActivity(activity: Activity) {
        if (activityStack == null) {
            activityStack = Stack<Activity>()
        }
        activityStack!!.add(activity)
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    val currentActivity: Activity
        get() {
            val activity = activityStack!!.lastElement()
            return activity
        }

    val preActivity: Activity?
        get() {
            val size = activityStack!!.size
            if (size < 2) return null
            return activityStack!!.elementAt(size - 2)
        }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    fun finishActivity() {
        val activity = activityStack!!.lastElement()
        finishActivity(activity)
    }

    /**
     * 结束指定的Activity
     */
    fun finishActivity(activity: Activity?) {
        var activity = activity
        if (activity != null) {
            activityStack!!.remove(activity)
            activity.finish()
            activity = null
        }
    }

    /**
     * 结束所有Activity
     */
    fun finishAllActivity() {
        var i = 0
        val size = activityStack!!.size
        while (i < size) {
            if (null != activityStack!![i]) {
                val activity = activityStack!![i]
                if (!activity.isFinishing) {
                    activity.finish()
                }
            }
            i++
        }
        activityStack!!.clear()
    }

    fun removeActivity(activity: Activity?) {
        if (activity != null) {
            activityStack!!.remove(activity)
        }
    }

    fun removeAllWithoutItself(activity: Activity) {
        activityStack!!.clear()
        addActivity(activity)
    }

    companion object {

        private var activityStack: Stack<Activity>? = null
    }
}