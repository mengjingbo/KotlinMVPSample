package com.mvp.sample.app.client

import rx.Observable
import rx.functions.Func1

/**
 * 作者：秦川小将
 * 时间：2018/3/28
 * 描述：
 */
class ErrorFunc<T> : Func1<Throwable, Observable<T>> {

    override fun call(t: Throwable?): Observable<T> {
        return Observable.error(Transformers.handleException(t))
    }
}