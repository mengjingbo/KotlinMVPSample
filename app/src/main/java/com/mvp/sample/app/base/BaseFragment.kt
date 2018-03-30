package com.mvp.sample.app.base

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

/**
 * 作者：蒙景博
 * 时间：2018/3/29
 * 描述：这里使用android.support.v4.app包下的Fragment作为基础类，
 *      可以与android.support.v4.viewViewPager结合使用。
 */
abstract class BaseFragment : Fragment() {

    /**
     * View初始化是否完成
     */
    open var isInitViewCompleted: Boolean = false

    /**
     * 是否设置懒加载模式,true：设置
     */
    open var isOpenLazyLoad: Boolean = false

    /**
     * 接收通过setArguments向Fragment中传递的Bundle对象
     */
    open lateinit var mBundle: Bundle

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var mView: View? = null
        if (mView == null) {
            mView = inflater?.inflate(layoutResId(), container, false)
        } else {
            var mViewGroup: ViewGroup = mView.parent as ViewGroup
            if (mViewGroup.childCount > 0) {
                mViewGroup.removeView(mView)
            }
        }
        return mView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isInitViewCompleted = true
        initView(view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (userVisibleHint) {
            mBundle = arguments
            loadData(mBundle)
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser && isInitViewCompleted && !isOpenLazyLoad) {
            loadData(mBundle)
        }
    }

    /**
     * 初始化Layout
     */
    abstract fun layoutResId(): Int

    /**
     * 初始化View
     */
    abstract fun initView(view: View?)

    /**
     * 加载数据
     */
    abstract fun loadData(bundle: Bundle?)

    /**
     * 消息提示
     */
    open fun showToast(message: String) {
        if (message.isEmpty()) return
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    /**
     * 消息提示
     */
    open fun showToast(resId: Int) {
        if (resId == 0) return
        Toast.makeText(activity, getString(resId), Toast.LENGTH_SHORT).show()
    }

    /**
     * 页面跳转
     */
    open fun <T> startActivity(clazz: Class<T>) {
        startActivity(Intent(activity, clazz))
    }

    /**
     * 页面跳转携带参数
     */
    open fun <T> startActivity(bundle: Bundle?, clazz: Class<T>) {
        startActivity(Intent(activity, clazz).putExtras(bundle))
    }

    /**
     * 添加android.support.v4.app包下的Fragment，方便与ViewPager使用
     */
    open fun addChildFragment(resId: Int, fragment: Fragment) {
        if (resId == 0 || (fragment == null || !fragment.isAdded)) return
        childFragmentManager.beginTransaction().add(resId, fragment).commit()

    }

    /**
     * 替换android.support.v4.app.Fragment
     */
    open fun replaceChildFragment(resId: Int, fragment: Fragment) {
        if (resId == 0 || fragment == null) return
        childFragmentManager.beginTransaction().replace(resId, fragment).commit()
    }

    /**
     * 删除已添加的Fragment
     */
    open fun removeChildFragment(fragment: Fragment) {
        if (fragment == null || !fragment.isAdded) return
        childFragmentManager.beginTransaction().remove(fragment).commit()
    }
}
