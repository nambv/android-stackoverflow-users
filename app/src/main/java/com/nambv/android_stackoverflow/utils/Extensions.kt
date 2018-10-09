@file:JvmName("ExtensionsUtils")

package com.nambv.android_stackoverflow.utils

import android.content.Context
import android.os.Build
import android.text.Html
import android.text.Spanned
import com.nambv.android_stackoverflow.R
import com.nambv.android_stackoverflow.utils.Constants.PAGE_SIZE
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.text.SimpleDateFormat
import java.util.*

fun Context.getErrorMessage(throwable: Throwable): String {
    return when (throwable) {
        is ConnectException -> getString(R.string.error_internet_connection)
        is UnknownHostException -> getString(R.string.error_internet_connection)
        is SocketTimeoutException -> getString(R.string.error_timeout)
        is HttpException -> getHttpExceptionMessage(throwable)
        else -> getString(R.string.error_unknown)
    }
}

private fun getHttpExceptionMessage(httpException: HttpException): String {
    return "#${httpException.code()}\n${httpException.message()}"
}

fun Long.toDate(): Date {
    return Date(this)
}

fun Date.toString(pattern: String): String {
    val formatter = SimpleDateFormat(pattern, Locale.getDefault())
    return formatter.format(this)
}

fun getOffsetByPage(page: Int): Int {
    return (page - 1) * PAGE_SIZE
}

@Suppress("DEPRECATION")
fun String.getHtmlText(): Spanned? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    } else {
        Html.fromHtml(this)
    }
}

fun getSelectedBookmarked(position: Int): Boolean? {
    return when (position) {
        0 -> null
        1 -> true
        else -> false
    }
}