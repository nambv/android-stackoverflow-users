package com.nambv.android_stackoverflow.view.base

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {

    protected var compositeDisposable: CompositeDisposable? = null

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