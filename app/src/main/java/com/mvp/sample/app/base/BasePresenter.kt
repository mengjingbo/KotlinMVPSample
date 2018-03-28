package com.mvp.sample.app.base

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent

/**
 * 作者：蒙景博
 * 时间：2018/3/27
 * 描述：
 */
abstract class BasePresenter<V : BaseView, M : BaseModel> : LifecycleObserver {

    protected open var mView: V? = null
    protected open var mModel: M? = null

    fun register(view: V, model: M) {
        this.mView = view
        this.mModel = model
    }

    /**
     * 当页面销毁时，这里利用生命周期组件对资源进行释放
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    private fun onDestroy() {
        if (mView != null) mView = null
        if (mModel != null) mModel = null
    }
}
