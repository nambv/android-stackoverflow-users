package com.nambv.android_stackoverflow.repository

import com.nambv.android_stackoverflow.data.User
import com.nambv.android_stackoverflow.data.local.dao.UserDao
import com.nambv.android_stackoverflow.service.UserApi
import com.nambv.android_stackoverflow.utils.BaseScheduler
import com.nambv.android_stackoverflow.utils.Constants
import com.nambv.android_stackoverflow.utils.getOffsetByPage
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UsersRepository constructor(private var userApi: UserApi, private var userDao: UserDao, private var scheduler: BaseScheduler) {

    companion object {

        private var instance: UsersRepository? = null

        fun getInstance(userApi: UserApi, userDao: UserDao, scheduler: BaseScheduler): UsersRepository {
            if (instance == null)
                instance = UsersRepository(userApi, userDao, scheduler)
            return instance as UsersRepository
        }
    }

    fun fetchUsers(page: Int, pageSize: Int, bookmarked: Boolean?): Single<List<User>> {
        return filterUsers(page, pageSize, bookmarked, userDao)
                .observeOn(scheduler.ui())
                .subscribeOn(scheduler.io())
    }

    fun updateUser(user: User): Completable {
        return Completable.fromAction { userDao.update(user) }
                .observeOn(scheduler.ui())
                .subscribeOn(scheduler.io())
    }

    private fun filterUsers(page: Int, pageSize: Int, bookmarked: Boolean?, userDao: UserDao): Single<List<User>> {

        if (null == bookmarked) {
            return userApi.fetchUsers(page, pageSize, Constants.SITE)
                    .map { response ->
                        if (response.users.isNotEmpty()) {
                            return@map response.users
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