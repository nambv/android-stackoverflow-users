package com.nambv.android_stackoverflow.repository

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.github.ajalt.timberkt.Timber
import com.nambv.android_stackoverflow.data.User
import com.nambv.android_stackoverflow.data.local.dao.UserDao
import com.nambv.android_stackoverflow.data.local.db.AppDatabase
import com.nambv.android_stackoverflow.data.remote.ApiService
import com.nambv.android_stackoverflow.service.UserApi
import com.nambv.android_stackoverflow.utils.Constants
import com.nambv.android_stackoverflow.utils.getOffsetByPage
import com.nambv.android_stackoverflow.view.result.UsersState
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class UsersRepository constructor(application: Application, userApi: UserApi, userDao: UserDao) {

    var usersDisposable: Disposable? = null
    var updateDisposable: Disposable? = null

    companion object {

        private var instance: UsersRepository? = null

        fun getInstance(application: Application, userApi: UserApi, userDao: UserDao): UsersRepository {
            if (instance == null)  // NOT thread safe!
                instance = UsersRepository(application, userApi, userDao)
            return instance!!
        }
    }

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

    private fun filterUsers(page: Int, pageSize: Int, bookmarked: Boolean?, userDao: UserDao): Single<List<User>> {

        val userApi: UserApi = ApiService.apiClient.create(UserApi::class.java)

        if (null == bookmarked) {
            return userApi.fetchUsers(page, pageSize, Constants.SITE)
                    .map { response ->
                        if (response.isSuccessful) {
                            response.body()?.let {

                                return@map it.users
                            }
                        } else {
                            throw RuntimeException("Error when fetch users")
                        }
                    }
                    .flatMap { remotes ->
                        if (remotes.isNotEmpty()) userDao.insert(remotes)
                        return@flatMap userDao.getUserList(pageSize, getOffsetByPage(page))
                    }
        } else {
            return userDao.searchUserList(bookmarked)
        }
    }
}