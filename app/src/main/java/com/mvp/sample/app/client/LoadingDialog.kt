package com.mvp.sample.app.client

import android.os.Bundle
import android.view.View
import com.mvp.sample.app.R
import com.mvp.sample.app.base.BaseDialog

/**
 * 作者：秦川小将
 * 时间：2018/3/28
 * 描述：
 */
class LoadingDialog : BaseDialog() {

    override fun layoutResId(): Int {
        return R.layout.dialog_loading
    }

    override fun initView(view: View?) {
    }

    override fun loadData(bundle: Bundle?) {
    }
}