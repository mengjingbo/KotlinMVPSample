package com.mvp.sample.app.client

import com.mvp.sample.app.BuildConfig
import com.mvp.sample.app.service.Action
import com.mvp.sample.app.service.GitHubService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * 作者：蒙景博
 * 时间：2018/3/28
 * 描述：
 */
class RetrofitClient private constructor() {

    private val mRetrofit: Retrofit
    private val mService: GitHubService
    private val mTimeout: Long = 60

    init {
        mRetrofit = retrofit
        mService = mRetrofit.create(GitHubService::class.java)
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
     * 初始化Retrofit
     */
    private val retrofit: Retrofit
        get() = Retrofit.Builder()
                .baseUrl(Action.API)
                .client(getClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build()

    /**
     * 初始化OkHttpClient
     */
    private val getClient: OkHttpClient
        get() = OkHttpClient.Builder()
                .connectTimeout(mTimeout, TimeUnit.SECONDS)
                .writeTimeout(mTimeout, TimeUnit.SECONDS)
                .readTimeout(mTimeout, TimeUnit.SECONDS)
                .addInterceptor(getLogInterceptor)
                .build()

    /**
     * 初始化HttpLoggingInterceptor
     */
    private val getLogInterceptor: HttpLoggingInterceptor
        get() {
            val mLogInterceptor = HttpLoggingInterceptor()
            if(BuildConfig.DEBUG){
                mLogInterceptor.level = HttpLoggingInterceptor.Level.BODY
            }
            return mLogInterceptor
        }

    /**
     * 返回一个Service拱外部使用
     */
    val service:GitHubService
        get() = mService
}