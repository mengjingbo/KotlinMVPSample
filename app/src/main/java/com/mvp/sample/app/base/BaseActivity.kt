package com.mvp.sample.app.base

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.widget.Toast

/**
 * 作者：秦川小将
 * 时间：2018/3/28
 * 描述：
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutResId)
        // 初始化
        initView()
        // 初始化数据
        loadData()
    }

    /**
     * 初始化视图
     */
    abstract val layoutResId: Int

    /**
     * 初始化View
     */
    abstract fun initView()

    /**
     * 初始化数据
     */
    abstract fun loadData()

    /**
     * 消息提示
     */
    open fun showToast(message: String) {
        if (message.isEmpty()) return
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    /**
     * 消息提示
     */
    open fun showToast(resId: Int) {
        if (resId == 0) return
        Toast.makeText(this, getString(resId), Toast.LENGTH_SHORT).show()
    }

    /**
     * 页面跳转
     */
    open fun <T> startActivity(clazz: Class<T>) {
        startActivity(Intent(this, clazz))
    }

    /**
     * 页面跳转携带参数
     */
    open fun <T> startActivity(bundle: Bundle?, clazz: Class<T>) {
        startActivity(Intent(this, clazz).putExtras(bundle))
    }

    /**
     * 添加android.support.v4.app.Fragment方便与ViewPager使用
     */
    open fun addFragment(resId: Int, fragment: Fragment?) {
        if (resId == 0 || (fragment == null || !fragment.isAdded)) return
        supportFragmentManager.beginTransaction().add(resId, fragment).commit()
    }

    /**
     * 替换android.support.v4.app.Fragment
     */
    open fun replaceFragment(resId: Int, fragment: Fragment?) {
        if (resId == 0 || fragment == null) return
        supportFragmentManager.beginTransaction().replace(resId, fragment).commit()
    }

    /**
     * 删除已添加的Fragment
     */
    @SuppressLint("CommitTransaction")
    open fun removeFragment(fragment: Fragment?) {
        if (fragment == null || !fragment.isAdded) return
        supportFragmentManager.beginTransaction().remove(fragment)
    }
}