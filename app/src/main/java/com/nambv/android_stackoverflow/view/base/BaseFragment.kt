package com.nambv.android_stackoverflow.view.base

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.view.View
import com.nambv.android_stackoverflow.MainApplication
import com.nambv.android_stackoverflow.R


abstract class BaseFragment : Fragment() {

    private var mActivity: BaseActivity? = null

    interface Callback {

        fun onFragmentAttached()

        fun onFragmentDetached(tag: String)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity) {
            mActivity = context
            context.onFragmentAttached()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView(view, savedInstanceState)
    }

    protected abstract fun setUpView(view: View, savedInstanceState: Bundle?)

    override fun onDetach() {
        mActivity = null
        super.onDetach()
    }

    private fun getBaseActivity(): BaseActivity? {
        return mActivity
    }

    override fun getContext(): Context {
        return mActivity ?: activity ?:MainApplication.appContext
    }

    protected fun findFragmentByTag(tag: String): Fragment {
        return childFragmentManager.findFragmentByTag(tag)
    }

    protected fun addFragment(containerViewId: Int, fragment: Fragment, tag: String?) {
        val fragmentTransaction = childFragmentManager.beginTransaction()
        fragmentTransaction.add(containerViewId, fragment, tag)
        fragmentTransaction.commit()
    }

    fun showError(message: String) {
        showToast(message)
    }

    fun showToast(message: String) {
        mActivity?.showToast(message)
    }

    fun showSnackBar(message: String) {
        mActivity?.showSnackBar(message)
    }

    fun hideLoading() {
        mActivity?.hideLoading()
    }

    fun showLoading(message: String, isCancelable: Boolean) {
        mActivity?.showLoading(message, isCancelable)
    }

    fun showLoading(message: String) {
        mActivity?.showLoading(message, false)
    }

    fun showErrorDialog(message: String) {
        getBaseActivity()?.let {
            val builder: AlertDialog.Builder = AlertDialog.Builder(it)
            builder.setTitle(getString(R.string.label_error))
                    .setMessage(message)
                    .setPositiveButton(android.R.string.ok, { dialog, which ->
                        // continue with OK
                    })
                    .show()
            return@let
        }
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

    interface InteractionListener {
        fun navigateToWelcomeScreen()
    }
}
