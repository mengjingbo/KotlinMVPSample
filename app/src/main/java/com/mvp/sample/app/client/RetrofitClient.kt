package com.mvp.sample.app.client

import com.mvp.sample.app.BuildConfig
import com.mvp.sample.app.bean.AccountBean
import com.mvp.sample.app.service.Action
import com.mvp.sample.app.service.GitHubService
import okhttp3.logging.HttpLoggingInterceptor
import rx.Observable

/**
 * 作者：秦川小将
 * 时间：2018/3/28
 * 描述：
 */
class RetrofitClient private constructor() {

    var mClientHelper: ClientHelper = ClientHelper().baseUrl(Action.API)

    init {
        if (BuildConfig.DEBUG) {
            mClientHelper.interceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }
    }

    companion object {

        @Volatile
        private var mHelper: RetrofitClient? = null
        val default: RetrofitClient?
            get() {
                if (mHelper == null) {
                    synchronized(RetrofitClient::class.java) {
                        if (mHelper == null) {
                            mHelper = RetrofitClient()
                        }
                    }
                }
                return mHelper
            }
    }

    /**
     * 获取账户信息
     */
    fun getAccount(name: String): Observable<AccountBean> {
        return mClientHelper.create(GitHubService::class.java).getAccount(name).compose(Transformers.transformer())
    }
}