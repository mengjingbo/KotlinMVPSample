package com.mvp.sample.app.base

import android.app.Activity
import android.app.DialogFragment
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.mvp.sample.app.R

/**
 * 作者：秦川小将
 * 时间：2017/5/18
 * 描述：基类Dialog
 */
abstract class BaseDialog : DialogFragment() {

    /**
     * 自定义时添加layout
     *
     * @return
     */
    protected abstract val layoutId: Int

    /**
     * 是否显示
     *
     * @return false:isHidden  true:isShowing
     */
    protected open val isShowing: Boolean
        get() = if (this.dialog != null) {
            this.dialog.isShowing
        } else {
            false
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 设置自定义样式
        if (setDialogStyle() == 0) {
            setStyle(DialogFragment.STYLE_NO_FRAME, R.style.dialog_style)
        } else {
            setStyle(DialogFragment.STYLE_NO_FRAME, setDialogStyle())
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle): View? {
        // 设置进场动画
        if (setWindowAnimationsStyle() != 0) {
            dialog.window!!.setWindowAnimations(setWindowAnimationsStyle())
        }
        // return 视图
        var mView: View? = null
        if (mView == null) {
            mView = inflater.inflate(layoutId, container, false)
        } else {
            if (mView != null) {
                val mViewGroup = mView.parent as ViewGroup
                mViewGroup?.removeView(mView)
            }
        }
        return mView
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loadData(arguments)
    }

    /**
     * 初始化
     *
     * @param view
     */
    protected abstract fun initView(view: View)


    /**
     * 加载数据
     *
     * @param bundle 用这个Bundle对象接收传入时的Bundle对象
     */
    protected abstract fun loadData(bundle: Bundle)

    /**
     * 创建视图
     *
     * @param context
     * @param resId
     * @return
     */
    protected open fun <T : View> createView(context: Context, resId: Int): T {
        return LayoutInflater.from(context).inflate(resId, null) as T
    }

    /**
     * 设置Dialog点击外部区域是否隐藏
     *
     * @param cancel
     */
    protected open fun setCanceledOnTouchOutside(cancel: Boolean) {
        if (dialog != null) {
            dialog.setCanceledOnTouchOutside(cancel)
        }
    }

    /**
     * 设置Dialog gravity
     *
     * @param gravity
     */
    protected open fun setGravity(gravity: Int) {
        if (dialog != null) {
            val mWindow = dialog.window
            val params = mWindow!!.attributes
            params.gravity = gravity
            mWindow.attributes = params
        }
    }

    /**
     * 设置Dialog窗口width
     *
     * @param width
     */
    protected open fun setDialogWidth(width: Int) {
        setDialogWidthAndHeight(width, LinearLayout.LayoutParams.WRAP_CONTENT)
    }

    /**
     * 设置Dialog窗口height
     *
     * @param height
     */
    protected open fun setDialogHeight(height: Int) {
        setDialogWidthAndHeight(LinearLayout.LayoutParams.WRAP_CONTENT, height)
    }

    /**
     * 设置Dialog窗口width，height
     *
     * @param width
     * @param height
     */
    protected open fun setDialogWidthAndHeight(width: Int, height: Int) {
        if (dialog != null) {
            dialog.window!!.setLayout(width, height)
        }
    }

    /**
     * 设置窗口转场动画
     *
     * @return
     */
    protected open fun setWindowAnimationsStyle(): Int {
        return 0
    }

    /**
     * 设置弹出框样式
     *
     * @return
     */
    protected open fun setDialogStyle(): Int {
        return 0
    }


    /**
     * 显示Dialog
     *
     * @param activity
     * @param tag      设置一个标签用来标记Dialog
     */
    open fun show(activity: Activity, tag: String) {
        show(activity, null, tag)
    }

    /**
     * 显示Dialog
     *
     * @param activity
     * @param bundle   要传递给Dialog的Bundle对象
     * @param tag      设置一个标签用来标记Dialog
     */
    open fun show(activity: Activity?, bundle: Bundle?, tag: String) {
        if (activity == null && isShowing) return
        val mTransaction = activity?.fragmentManager?.beginTransaction()
        val mFragment = activity?.fragmentManager?.findFragmentByTag(tag)
        if (mFragment != null) {
            //为了不重复显示dialog，在显示对话框之前移除正在显示的对话框
            mTransaction?.remove(mFragment)
        }
        if (bundle != null) {
            arguments = bundle
        }
        show(mTransaction, tag)
    }
}
