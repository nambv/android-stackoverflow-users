package com.nambv.android_stackoverflow.view.base

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.View
import com.nambv.android_stackoverflow.MainApplication


abstract class BaseFragment<T : BaseViewModel> : Fragment() {

    lateinit var viewModel: T
    var pActivity: BaseActivity? = null

    protected abstract fun initViewModel(): T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = initViewModel()
        setupView()
    }

    abstract fun setupView()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.let { pActivity = it as BaseActivity }
    }

    private fun getBaseActivity(): BaseActivity? {
        return pActivity
    }

    override fun getContext(): Context {
        return pActivity ?: activity ?: MainApplication.appContext
    }

    protected fun findFragmentByTag(tag: String): Fragment {
        return childFragmentManager.findFragmentByTag(tag)
    }

    protected fun addFragment(containerViewId: Int, fragment: Fragment, tag: String?) {
        val fragmentTransaction = childFragmentManager.beginTransaction()
        fragmentTransaction.add(containerViewId, fragment, tag)
        fragmentTransaction.commit()
    }

    fun showToast(message: String) {
        pActivity?.showToast(message)
    }

    fun hideLoading() {
        pActivity?.hideLoading()
    }

    fun showLoading(message: String, isCancelable: Boolean) {
        pActivity?.showLoading(message, isCancelable)
    }

    fun showLoading(message: String) {
        pActivity?.showLoading(message, false)
    }

    fun showDialogMessage(title: String, message: String, positiveListener: DialogInterface.OnClickListener) {
        getBaseActivity()?.let {
            val builder: AlertDialog.Builder = AlertDialog.Builder(it)
            builder.setTitle(title)
                    .setMessage(message)
                    .setPositiveButton(android.R.string.ok, positiveListener)
                    .setNegativeButton(android.R.string.cancel) { dialogInterface, _ -> dialogInterface.dismiss() }
                    .show()
            return@let
        }
    }

    override fun onDestroy() {
        viewModel.unSubscribe()
        super.onDestroy()
    }
}
