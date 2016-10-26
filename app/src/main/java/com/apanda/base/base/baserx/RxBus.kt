package com.apanda.base.base.baserx

import com.apanda.base.Utils.logger.L

import java.util.ArrayList
import java.util.concurrent.ConcurrentHashMap

import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Action1
import rx.subjects.PublishSubject
import rx.subjects.Subject

/**
 * 用RxJava实现的EventBus
 * Created by xsf
 * on 2016.08.14:50
 */
class RxBus private constructor() {

    @SuppressWarnings("rawtypes")
    private val subjectMapper = ConcurrentHashMap<Any, List<Subject<*, *>>>()

    /**
     * 订阅事件源

     * @param mObservable
     * *
     * @param mAction1
     * *
     * @return
     */
    fun OnEvent(mObservable: Observable<*>, mAction1: Action1<Any>): RxBus {
        mObservable.observeOn(AndroidSchedulers.mainThread()).subscribe(mAction1, Action1<kotlin.Throwable> { throwable -> throwable.printStackTrace() })
        return getInstance()
    }

    /**
     * 注册事件源

     * @param tag
     * *
     * @return
     */
    @SuppressWarnings("rawtypes")
    fun <T> register(tag: Any): Observable<T> {
        var subjectList: MutableList<Subject<*, *>>? = subjectMapper[tag]
        if (null == subjectList) {
            subjectList = ArrayList<Subject<*, *>>()
            subjectMapper.put(tag, subjectList)
        }
        val subject: Subject<T, T>
        subjectList.add(subject = PublishSubject.create<T>())
        L.d("register" + tag + "  size:" + subjectList.size)
        return subject
    }

    @SuppressWarnings("rawtypes")
    fun unregister(tag: Any) {
        val subjects = subjectMapper[tag]
        if (null != subjects) {
            subjectMapper.remove(tag)
        }
    }

    /**
     * 取消监听

     * @param tag
     * *
     * @param observable
     * *
     * @return
     */
    @SuppressWarnings("rawtypes")
    fun unregister(tag: Any,
                   observable: Observable<*>): RxBus {
        if (null == observable)
            return getInstance()
        val subjects = subjectMapper[tag]
        if (null != subjects) {
            subjects.remove(observable)
            if (isEmpty(subjects)) {
                subjectMapper.remove(tag)
                L.d("unregister" + tag + "  size:" + subjects.size)
            }
        }
        return getInstance()
    }

    fun post(content: Any) {
        post(content.javaClass.name, content)
    }

    /**
     * 触发事件

     * @param content
     */
    @SuppressWarnings("unchecked", "rawtypes")
    fun post(tag: Any, content: Any) {
        L.d("post" + "eventName: " + tag)
        val subjectList = subjectMapper[tag]
        if (!isEmpty(subjectList)) {
            for (subject in subjectList) {
                subject.onNext(content)
                L.d("onEvent" + "eventName: " + tag)
            }
        }
    }

    companion object {
        private var instance: RxBus? = null

        @Synchronized fun getInstance(): RxBus {
            if (null == instance) {
                instance = RxBus()
            }
            return instance
        }

        @SuppressWarnings("rawtypes")
        fun isEmpty(collection: Collection<Subject<*, *>>?): Boolean {
            return null == collection || collection.isEmpty()
        }
    }

}
