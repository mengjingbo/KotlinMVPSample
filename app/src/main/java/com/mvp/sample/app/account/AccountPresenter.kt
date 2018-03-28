package com.mvp.sample.app.account

import android.app.Activity
import android.util.Log
import com.mvp.sample.app.bean.AccountBean
import com.mvp.sample.app.client.LoadingDialog
import rx.Subscriber

/**
 * 作者：秦川小将
 * 时间：2018/3/27
 * 描述：
 */
class AccountPresenter : AccountContract.Presenter() {

    private val mLoading: LoadingDialog = LoadingDialog()

    override fun getAccount(activity: Activity, name: String) {
        mModel?.getAccount(name)?.subscribe(object : Subscriber<AccountBean>() {

            override fun onStart() {
                super.onStart()
                Log.e("com.mvp.sample.app", "onStart()")
                mLoading.showDialog(activity, "load")
            }

            override fun onNext(t: AccountBean?) {
                if (mView != null) {
                    if (t != null) {
                        mView?.setAccount(t)
                    } else {
                        mView?.onMessage("返回数据为空")
                    }
                }
            }

            override fun onCompleted() {
                mLoading.dismiss()
            }

            override fun onError(e: Throwable?) {
                mLoading.dismiss()
                if (e?.message?.isNotEmpty()!!) mView?.onMessage(e.message!!)
            }
        })
    }
}
