package com.mvp.sample.app.account

import com.mvp.sample.app.bean.AccountBean
import com.mvp.sample.app.client.ClientHelper
import com.mvp.sample.app.client.RetrofitClient
import com.mvp.sample.app.client.Transformers
import com.mvp.sample.app.service.Action
import com.mvp.sample.app.service.GitHubService
import okhttp3.logging.HttpLoggingInterceptor
import org.jetbrains.annotations.NotNull
import rx.Observable


/**
 * 作者：秦川小将
 * 时间：2018/3/27
 * 描述：
 */
class AccountModel : AccountContract.Model() {

    @NotNull
    override fun getAccount(name: String): Observable<AccountBean> {
        // 调用方式1
        return RetrofitClient.default!!.getAccount(name)
        // 调用方式2
//        return ClientHelper()
//                .baseUrl(Action.API)
//                .interceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
//                .create(GitHubService::class.java)
//                .getAccount(name)
//                .compose(Transformers.transformer())
    }
}
