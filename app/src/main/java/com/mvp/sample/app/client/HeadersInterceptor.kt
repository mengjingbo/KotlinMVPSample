package com.mvp.sample.app.client

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * 作者：秦川小将
 * 时间：2018/3/28
 * 描述：
 */
class HeadersInterceptor(private val headers: Map<String, String>) : Interceptor {

    override fun intercept(chain: Interceptor.Chain?): Response {
        val builder: Request.Builder = chain!!.request().newBuilder()
        if (headers.isNotEmpty()) {
            val mKeys: Set<String> = headers.keys
            mKeys.forEach {
                builder.addHeader(it, headers[it]).build()
            }
        }
        return chain.proceed(builder.build())
    }
}