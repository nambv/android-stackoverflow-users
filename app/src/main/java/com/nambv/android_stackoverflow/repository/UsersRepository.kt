package com.nambv.android_stackoverflow.repository

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.nambv.android_stackoverflow.data.local.db.AppDatabase
import com.nambv.android_stackoverflow.service.UserService
import com.nambv.android_stackoverflow.view.result.UsersState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

object UsersRepository {

    private var usersDisposable: Disposable? = null

    fun fetchUsers(application: Application, page: Int, pageSize: Int): MutableLiveData<UsersState> {

        val usersLiveData = MutableLiveData<UsersState>()

        if (page > 1)
            usersLiveData.postValue(UsersState.LoadMore)
        else
            usersLiveData.postValue(UsersState.Refreshing)

        val userDao = AppDatabase.get(application).userDao()

        usersDisposable?.let { if (usersDisposable?.isDisposed == false) usersDisposable?.dispose() }

        usersDisposable = UserService.fetchUsers(page, pageSize)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        {
                            if (it.isNotEmpty()) {
                                usersLiveData.postValue(UsersState.Data(it))
                            }
                        },
                        {
                            usersLiveData.postValue(UsersState.Error(it))
                        })

        return usersLiveData
    }
}