package com.mvp.sample.app.account

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.mvp.sample.app.R
import com.mvp.sample.app.bean.AccountBean
import kotlinx.android.synthetic.main.activity_main.*

class AccountActivity : AppCompatActivity(), AccountContract.View {

    private var mPresenter = AccountPresenter()
    private var mModel = AccountModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // 注册View和Model
        mPresenter.register(this, mModel)
        // 添加生命周期组件
        lifecycle.addObserver(mPresenter)
        // 发起请求
        button.setOnClickListener {
            val name: String = name_edit.text.toString()
            if (!name.isEmpty()) {
                mPresenter.getAccount(this, name)
            }else{
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
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
