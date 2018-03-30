package com.mvp.sample.app.account

import com.mvp.sample.app.R
import com.mvp.sample.app.base.BaseActivity
import com.mvp.sample.app.bean.AccountBean
import kotlinx.android.synthetic.main.activity_main.*

/**
 * 作者：秦川小将
 * 时间：2018/3/27
 * 描述：GitHub账户
 */
class AccountActivity : BaseActivity(), AccountContract.View {

    private var mPresenter = AccountPresenter()
    private var mModel = AccountModel()

    override val layoutResId: Int
        get() = R.layout.activity_main

    override fun initView() {
        mPresenter.register(this, mModel) // 注册View和Model
        lifecycle.addObserver(mPresenter) // 添加生命周期组件
    }

    override fun loadData() {
        // 发起请求
        button.setOnClickListener {
            val name: String = name_edit.text.toString()
            if (!name.isEmpty()) {
                mPresenter.getAccount(this, name)
            } else {
                onMessage("请输入GitHub帐户名")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // 删除生命周期组件
        lifecycle.removeObserver(mPresenter)
    }

    override fun setAccount(account: AccountBean) {
        profile_text.text = account.accountToString()
    }

    override fun onMessage(message: String) {
        showToast(message)
    }
}
