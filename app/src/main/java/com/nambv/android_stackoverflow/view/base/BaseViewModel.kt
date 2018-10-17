package com.nambv.android_stackoverflow.view.base

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.nambv.android_stackoverflow.data.local.db.AppDatabase
import com.nambv.android_stackoverflow.data.remote.ApiService
import com.nambv.android_stackoverflow.service.UserApi
import com.nambv.android_stackoverflow.utils.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {

    internal var userApi = ApiService.apiClient.create(UserApi::class.java)
    internal var userDao = AppDatabase.get(application).userDao()
    internal var baseScheduler = SchedulerProvider()

    private var compositeDisposable: CompositeDisposable? = null

    fun addSubscribe(disposable: Disposable?) {

        if (compositeDisposable == null) {
            compositeDisposable = CompositeDisposable()
        }

        if (disposable != null) {
            compositeDisposable?.add(disposable)
        }
    }

    override fun onCleared() {
        super.onCleared()
        unSubscribe()
    }

    fun unSubscribe() {
        compositeDisposable?.dispose()
    }
}