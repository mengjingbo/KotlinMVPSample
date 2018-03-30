package com.mvp.sample.app.client

import android.net.ParseException
import com.google.gson.JsonParseException
import org.json.JSONException
import retrofit2.HttpException
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.net.ConnectException
import java.net.SocketTimeoutException

/**
 * 作者：秦川小将
 * 时间：2018/3/28
 * 描述：订阅/取消/响应 切换线程处理异常
 */
object Transformers {

    fun <T> transformer(): Observable.Transformer<T, T> {
        return Observable.Transformer { observable ->
            observable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .onErrorResumeNext(ErrorFunc<T>())
        }
    }

    /**
     * API常见异常统一处理
     */
    fun handleException(e: Throwable?): ApiException {
        val mApiEx: ApiException
        if (e is ApiException) {
            val mHttpException: HttpException = e as HttpException
            mApiEx = ApiException(e, ResultCode.Request.HTTP_ERROR)
            when (mHttpException.code()) { // 均视为网络错误，详情见状态码注释部分。
                ResultCode.Http.UNAUTHORIZED,
                ResultCode.Http.FORBIDDEN,
                ResultCode.Http.NOT_FOUND,
                ResultCode.Http.REQUEST_TIMEOUT,
                ResultCode.Http.GATEWAY_TIMEOUT,
                ResultCode.Http.INTERNAL_SERVER_ERROR,
                ResultCode.Http.BAD_GATEWAY,
                ResultCode.Http.SERVICE_UNAVAILABLE->{
                    mApiEx.msg = "网络错误"
                }
                else -> {
                    mApiEx.msg = "网络错误"
                }
            }
            return mApiEx
        } else if (e is JsonParseException || e is JSONException || e is ParseException) {
            mApiEx = ApiException(e, ResultCode.Request.PARSE_ERROR)
            mApiEx.msg = "解析错误"
            return mApiEx
        } else if (e is ConnectException) {
            mApiEx = ApiException(e, ResultCode.Request.NETWORK_ERROR)
            mApiEx.msg = "链接超时"
            return mApiEx
        } else if (e is SocketTimeoutException) {
            mApiEx = ApiException(e, ResultCode.Request.TIMEOUT_ERROR)
            mApiEx.msg = "请求超时"
            return mApiEx
        } else {
            mApiEx = ApiException(e, ResultCode.Request.UNKNOWN)
            mApiEx.msg = "未知错误"
            return mApiEx
        }
    }
}