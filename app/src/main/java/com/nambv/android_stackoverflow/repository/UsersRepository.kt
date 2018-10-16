package com.nambv.android_stackoverflow.repository

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.github.ajalt.timberkt.Timber
import com.nambv.android_stackoverflow.data.User
import com.nambv.android_stackoverflow.data.local.dao.UserDao
import com.nambv.android_stackoverflow.data.local.db.AppDatabase
import com.nambv.android_stackoverflow.service.UserService
import com.nambv.android_stackoverflow.utils.getOffsetByPage
import com.nambv.android_stackoverflow.view.result.UsersState
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

object UsersRepository {

    var usersDisposable: Disposable? = null
    var updateDisposable: Disposable? = null

    fun fetchUsers(application: Application, page: Int, pageSize: Int, bookmarked: Boolean?): LiveData<UsersState> {

        val usersLiveData = MutableLiveData<UsersState>()

        if (page > 1)
            usersLiveData.setValue(UsersState.LoadMore)
        else
            usersLiveData.setValue(UsersState.Refreshing)

        val userDao = AppDatabase.get(application).userDao()

        usersDisposable?.let { if (usersDisposable?.isDisposed == false) usersDisposable?.dispose() }

        usersDisposable = filterUsers(page, pageSize, bookmarked, userDao)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        {
                            usersLiveData.setValue(UsersState.Data(it))
                        },
                        {
                            usersLiveData.setValue(UsersState.Error(it))
                        })

        return usersLiveData
    }

    fun updateUser(application: Application, user: User): MutableLiveData<UsersState> {

        updateDisposable?.let { if (updateDisposable?.isDisposed == false) updateDisposable?.dispose() }

        val userLiveData = MutableLiveData<UsersState>()
        val userDao = AppDatabase.get(application).userDao()

        updateDisposable = Completable.fromAction { userDao.update(user) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        {
                            Timber.w { "bookmarked: ${user.bookmarked}" }
                            userLiveData.setValue(UsersState.Updated)
                        },
                        {
                            userLiveData.setValue(UsersState.Error(it))
                        })

        return userLiveData
    }

    private fun filterUsers(page: Int, pageSize: Int, bookmarked: Boolean?, userDao: UserDao): Maybe<List<User>> {

        if (null == bookmarked) {
            return UserService.fetchUsers(page, pageSize)
                    .flatMapMaybe { remotes ->
                        if (remotes.isNotEmpty()) userDao.insert(remotes)
                        return@flatMapMaybe userDao.getUserList(pageSize, getOffsetByPage(page))
                    }
        } else {
            return userDao.searchUserList(bookmarked)
        }
    }
}