package com.mvp.sample.app.account

import com.mvp.sample.app.client.Transformers
import com.mvp.sample.app.bean.AccountBean
import com.mvp.sample.app.client.RetrofitClient
import org.jetbrains.annotations.NotNull
import rx.Observable


/**
 * 作者：蒙景博
 * 时间：2018/3/27
 * 描述：
 */
class AccountModel : AccountContract.Model() {

    @NotNull
    override fun getAccount(name: String): Observable<AccountBean> {
        return RetrofitClient.default!!.service.getAccount(name).map { accountBean -> accountBean }.compose(Transformers.transformer())
    }
}
