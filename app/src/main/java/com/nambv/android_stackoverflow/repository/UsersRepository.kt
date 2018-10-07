package com.nambv.android_stackoverflow.repository

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import com.nambv.android_stackoverflow.data.local.db.AppDatabase
import com.nambv.android_stackoverflow.service.UserService
import com.nambv.android_stackoverflow.view.result.UsersState
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object UsersRepository {

    fun fetchUsers(application: Application): MutableLiveData<UsersState> {

        val usersLiveData = MutableLiveData<UsersState>()

        val userDao = AppDatabase.get(application).userDao()

        userDao.getUserList()
                .flatMapSingle {
                    if (it.isNotEmpty())
                        usersLiveData.postValue(UsersState.Data(it))
                    return@flatMapSingle UserService.fetchUsers()
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        {
                            if (it.isNotEmpty()) {
                                usersLiveData.postValue(UsersState.Data(it))
                                userDao.insert(it)
                            }
                        },
                        {
                            usersLiveData.postValue(UsersState.Error(it))
                        })

        return usersLiveData
    }
}