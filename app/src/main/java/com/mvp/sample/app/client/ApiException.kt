package com.mvp.sample.app.client

/**
 * 作者：秦川小将
 * 时间：2018/3/28
 * 描述：
 */
class ApiException(throwable: Throwable?, var code: Int) : Throwable(throwable) {

    lateinit var msg: String

}
