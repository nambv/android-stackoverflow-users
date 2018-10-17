package com.nambv.android_stackoverflow.utils

import android.support.annotation.NonNull
import io.reactivex.Scheduler

interface BaseScheduler {

    @NonNull
    fun io(): Scheduler

    @NonNull
    fun ui(): Scheduler
}