package com.nambv.android_stackoverflow

import android.app.Application
import android.content.Context
import com.nambv.android_stackoverflow.utils.LoggerManager
import timber.log.Timber

/**
 * Main Application
 */
class MainApplication : Application() {

    companion object {
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(LoggerManager())
        appContext = applicationContext
    }
}