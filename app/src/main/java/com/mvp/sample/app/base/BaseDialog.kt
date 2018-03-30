package com.mvp.sample.app.base

import android.app.Activity
import android.app.DialogFragment
import android.app.FragmentTransaction
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import com.mvp.sample.app.R

/**
 * 作者：秦川小将
 * 时间：2018/3/28
 * 描述：
 */
abstract class BaseDialog : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 设置自定义样式
        if (setDialogStyle() == 0) {
            setStyle(DialogFragment.STYLE_NO_FRAME, R.style.dialog_style)
        } else {
            setStyle(DialogFragment.STYLE_NO_FRAME, setDialogStyle())
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // 设置进场动画
        if (setWindowAnimationsStyle() != 0) {
            dialog.window.setWindowAnimations(setWindowAnimationsStyle())
        }
        // 返回视图
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
        initView(view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loadData(arguments)
    }

    /**
     * 设置自定义样式
     */
    open fun setDialogStyle(): Int {
        return 0
    }

    /**
     * 设置窗口转场动画
     */
    open fun setWindowAnimationsStyle(): Int {
        return 0
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
    abstract fun loadData(arguments: Bundle?)

    /**
     * 设置Dialog窗口width
     */
    open fun setDialogWidth(width: Int) {
        setDialogWidthAndHeight(width, LinearLayout.LayoutParams.WRAP_CONTENT)
    }

    /**
     * 设置Dialog窗口height
     */
    open fun setDialogHeight(height: Int) {
        setDialogWidthAndHeight(LinearLayout.LayoutParams.WRAP_CONTENT, height)
    }

    /**
     * 设置Dialog窗口width和height
     */
    open fun setDialogWidthAndHeight(width: Int, height: Int) {
        if (dialog != null) dialog.window.setLayout(width, height)
    }

    /**
     * 设置Dialog gravity
     */
    open fun setGravity(gravity: Int) {
        if (dialog != null) {
            val mWindow: Window = dialog.window
            val mParams: WindowManager.LayoutParams = mWindow.attributes
            mParams.gravity = gravity
            mWindow.attributes = mParams
        }
    }

    /**
     * showDialog
     */
    open fun showDialog(activity: Activity, tag: String) {
        showDialog(activity, null, tag)
    }

    /**
     * showDialog
     */
    open fun showDialog(activity: Activity, bundle: Bundle?, tag: String) {
        val mTransaction: FragmentTransaction = activity.fragmentManager.beginTransaction()
        if (activity.fragmentManager.findFragmentByTag(tag) != null) {
            mTransaction.remove(activity.fragmentManager.findFragmentByTag(tag))
        }
        if (bundle != null) {
            arguments = bundle
        }
        show(mTransaction, tag)
    }
}