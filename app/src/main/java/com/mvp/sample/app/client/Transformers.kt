package com.mvp.sample.app.client

import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

/**
 * 作者：秦川小将
 * 时间：2018/3/28
 * 描述：订阅/取消/响应 线程
 */
object Transformers {

    fun <T> transformer(): Observable.Transformer<T, T> {
        return Observable.Transformer { observable -> observable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
        }
    }
}