package com.nambv.android_stackoverflow.view.result

import android.app.Application
import android.arch.lifecycle.LiveData
import com.nambv.android_stackoverflow.data.User
import com.nambv.android_stackoverflow.repository.UsersRepository
import com.nambv.android_stackoverflow.view.base.BaseViewModel

class UsersViewModel(application: Application) : BaseViewModel(application) {

    private var usersRepository: UsersRepository = UsersRepository(getApplication(), userApi, userDao)

    private lateinit var usersLiveData: LiveData<UsersState>
    private lateinit var userLiveData: LiveData<UsersState>

    fun fetchUsers(page: Int, pageSize: Int, bookmarked: Boolean?): LiveData<UsersState> {
        usersLiveData = usersRepository.fetchUsers(getApplication(), page, pageSize, bookmarked)
        addSubscribe(usersRepository.usersDisposable)
        return usersLiveData
    }

    fun updateUser(user: User): LiveData<UsersState> {
        userLiveData = usersRepository.updateUser(getApplication(), user)
        addSubscribe(usersRepository.updateDisposable)
        return userLiveData
    }
}