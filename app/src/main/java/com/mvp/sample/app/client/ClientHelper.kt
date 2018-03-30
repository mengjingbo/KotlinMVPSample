package com.mvp.sample.app.client

import okhttp3.*
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.net.Proxy
import java.util.concurrent.TimeUnit

/**
 * 作者：秦川小将
 * 时间：2018/3/28
 * 描述：Http请求Helper
 */
class ClientHelper {

    companion object {
        const val DEFAULT_TIMEOUT: Long = 60 // 默认超时时间
        const val CACHE_MAX_SIZE: Long = 10 * 1024 * 1024 // 默认最大缓存大小
    }

    // Retrofit, OkHttpClient
    private lateinit var mRetrofit: Retrofit
    private var mOkHttpClient: OkHttpClient? = null
    private var mOkHttpBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
    private var mRetrofitBuilder: Retrofit.Builder = Retrofit.Builder()
    // 缓存
    private var isCache: Boolean = false
    private var mCache: Cache? = null
    private var mHttpCacheFile: File? = null
    // Url
    private var mBaseUrl: String? = null
    // Factory
    private var mCallFactory: Call.Factory? = null
    private var mConverterFactory: Converter.Factory? = null
    private var mCallAdapterFactory: CallAdapter.Factory? = null

    /**
     * 设置自定义OkHttpClient
     */
    fun client(okHttpClient: OkHttpClient): ClientHelper {
        this.mOkHttpClient = okHttpClient
        return this
    }

    /**
     * 设置baseUrl
     */
    fun baseUrl(url: String): ClientHelper {
        this.mBaseUrl = url
        return this
    }

    /**
     * 设置链接超时时间（秒）
     */
    fun connectTimeout(timeout: Long): ClientHelper {
        connectTimeout(timeout, TimeUnit.SECONDS)
        return this
    }

    /**
     * 设置链接超时时间（秒）
     */
    fun connectTimeout(timeout: Long, unit: TimeUnit): ClientHelper {
        if (timeout > -1) {
            mOkHttpBuilder.connectTimeout(timeout, unit)
        } else {
            mOkHttpBuilder.connectTimeout(ClientHelper.DEFAULT_TIMEOUT, unit)
        }
        return this
    }

    /**
     * 设置写入超时时间（秒）
     */
    fun writeTimeout(timeout: Long, unit: TimeUnit): ClientHelper {
        if (timeout > -1) {
            mOkHttpBuilder.writeTimeout(timeout, unit)
        } else {
            mOkHttpBuilder.writeTimeout(ClientHelper.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
        }
        return this
    }

    /**
     * 设置读取超时时间（秒）
     */
    fun readTimeout(timeout: Long, unit: TimeUnit): ClientHelper {
        if (timeout > -1) {
            mOkHttpBuilder.readTimeout(timeout, unit)
        } else {
            mOkHttpBuilder.readTimeout(ClientHelper.DEFAULT_TIMEOUT, TimeUnit.SECONDS)
        }
        return this
    }

    /**
     * 设置Call.Factory
     */
    fun callFactory(factory: Call.Factory): ClientHelper {
        this.mCallFactory = factory
        return this
    }

    fun converterFactory(factory: Converter.Factory): ClientHelper {
        this.mConverterFactory = factory
        return this
    }

    fun callAdapterFactory(factory: CallAdapter.Factory): ClientHelper {
        this.mCallAdapterFactory = factory
        return this
    }

    /**
     * 设置请求头
     */
    fun headers(headers: Map<String, String>): ClientHelper {
        this.mOkHttpBuilder.addInterceptor(HeadersInterceptor(headers))
        return this
    }

    /**
     * 设置请求固定参数
     */
    fun parameters(parameters: Map<String, String>): ClientHelper {
        this.mOkHttpBuilder.addInterceptor(HeadersInterceptor(parameters))
        return this
    }

    /**
     * 添加拦截器
     */
    fun interceptor(interceptor: Interceptor): ClientHelper {
        this.mOkHttpBuilder.addInterceptor(checkNotNull(interceptor))
        return this
    }

    /**
     * 设置网络拦截器
     */
    fun networkInterceptor(interceptor: Interceptor): ClientHelper {
        this.mOkHttpBuilder.addNetworkInterceptor(checkNotNull(interceptor))
        return this
    }

    /**
     * 设置代理
     */
    fun proxy(proxy: Proxy): ClientHelper {
        this.mOkHttpBuilder.proxy(checkNotNull(proxy))
        return this
    }

    /**
     * 设置Dns
     */
    fun dns(dns: Dns): ClientHelper {
        this.mOkHttpBuilder.dns(dns)
        return this
    }

    /**
     * 设置缓存文件
     */
    fun cacheDir(file: File): ClientHelper {
        this.mHttpCacheFile = file
        return this
    }

    /**
     * 是否开启缓存
     */
    fun cache(isCache: Boolean): ClientHelper {
        this.isCache = isCache
        return this
    }

    fun <T> create(service: Class<T>): T {
        // 设置Url
        mRetrofitBuilder.baseUrl(checkNotNull(mBaseUrl))
        // 设置转换
        if (mConverterFactory == null) {
            mConverterFactory = GsonConverterFactory.create()
        }
        mRetrofitBuilder.addConverterFactory(mConverterFactory)
        // 设置Adapter
        if (mCallAdapterFactory == null) {
            mCallAdapterFactory = RxJavaCallAdapterFactory.create()
        }
        mRetrofitBuilder.addCallAdapterFactory(mCallAdapterFactory)
        // 设置自定义
        if (mCallFactory != null) {
            mRetrofitBuilder.callFactory(mCallFactory)
        }
        // 设置缓存
        if (isCache) {
            if (mHttpCacheFile != null) {
                mCache = Cache(mHttpCacheFile, ClientHelper.CACHE_MAX_SIZE)
            }
        }
        mOkHttpClient = mOkHttpBuilder.build()
        mRetrofitBuilder.client(mOkHttpClient)
        mRetrofit = mRetrofitBuilder.build()
        return mRetrofit.create(service)
    }
}
