package com.mvp.sample.app.service

import com.mvp.sample.app.bean.AccountBean
import retrofit2.http.GET
import retrofit2.http.Path
import rx.Observable

/**
 * 作者：秦川小将
 * 时间：2018/3/27
 * 描述：
 */
interface GitHubService {

    @GET("users/{name}")
    fun getAccount(@Path("name") user: String): Observable<AccountBean>
}