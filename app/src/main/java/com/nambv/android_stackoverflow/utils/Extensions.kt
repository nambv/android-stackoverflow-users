@file:JvmName("ExtensionsUtils")

package com.nambv.android_stackoverflow.utils

import android.content.Context
import com.nambv.android_stackoverflow.R
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