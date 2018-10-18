package com.nambv.android_stackoverflow.utils

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SchedulerProvider : BaseScheduler {
    override fun io() = Schedulers.io()
    override fun ui() = AndroidSchedulers.mainThread()
}