@file:Suppress("DEPRECATION")

package com.nambv.android_stackoverflow.view.base

import android.app.ProgressDialog
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.nambv.android_stackoverflow.utils.Constants


@Suppress("DEPRECATION")
abstract class BaseActivity : AppCompatActivity() {

    private var progressDialog: ProgressDialog? = null
    private var alertDialog: AlertDialog? = null

    fun showToast(message: String?) {
        Toast.makeText(this, message ?: Constants.EMPTY, Toast.LENGTH_SHORT).show()
    }

    fun hideLoading() {
        progressDialog?.isShowing?.let { progressDialog?.dismiss() }
    }

    fun showLoading(message: String, isCancelable: Boolean) {
        hideLoading()
        progressDialog = ProgressDialog.show(this, null, message, true)
        progressDialog?.let {
            it.show()
            it.isIndeterminate = true
            it.setCancelable(isCancelable)
            it.setCanceledOnTouchOutside(false)
        }
    }

    fun showLoading(message: String) {
        showLoading(message, false)
    }

    fun showAlertDialog(title: String?, message: String?,
                        positiveText: String?, positiveClickListener: DialogInterface.OnClickListener?,
                        negativeText: String?, negativeClickListener: DialogInterface.OnClickListener?) {
        if (null == alertDialog)
            alertDialog = AlertDialog.Builder(this).create()

        alertDialog?.let { dialog ->

            title?.let { dialog.setTitle(it) }
            message?.let { dialog.setMessage(it) }
            positiveText?.let {
                dialog.setButton(AlertDialog.BUTTON_POSITIVE, positiveText, positiveClickListener)
            }

            positiveText?.let {
                dialog.setButton(AlertDialog.BUTTON_NEGATIVE, negativeText, negativeClickListener)
            }

            alertDialog?.show()
            return@let
        }
    }
}
