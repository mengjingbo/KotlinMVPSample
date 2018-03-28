package com.mvp.sample.app.account

import android.app.Activity
import com.mvp.sample.app.base.BaseModel
import com.mvp.sample.app.base.BasePresenter
import com.mvp.sample.app.base.BaseView
import com.mvp.sample.app.bean.AccountBean
import rx.Observable

/**
 * 作者：秦川小将
 * 时间：2018/3/27
 * 描述：
 */
interface AccountContract {

    interface View : BaseView {

        fun setAccount(account: AccountBean)

    }

    abstract class Model : BaseModel() {

        abstract fun getAccount(name: String): Observable<AccountBean>
    }

    abstract class Presenter : BasePresenter<View, Model>() {

        abstract fun getAccount(activity: Activity, name: String)
    }
}
