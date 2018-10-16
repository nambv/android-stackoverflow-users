package com.nambv.android_stackoverflow.view.result

import android.app.Application
import android.arch.lifecycle.LiveData
import com.nambv.android_stackoverflow.data.User
import com.nambv.android_stackoverflow.repository.UsersRepository
import com.nambv.android_stackoverflow.view.base.BaseViewModel

class UsersViewModel(application: Application) : BaseViewModel(application) {

    private lateinit var usersLiveData: LiveData<UsersState>
    private lateinit var userLiveData: LiveData<UsersState>

    fun fetchUsers(page: Int, pageSize: Int, bookmarked: Boolean?): LiveData<UsersState> {
        usersLiveData = UsersRepository.fetchUsers(getApplication(), page, pageSize, bookmarked)
        addSubscribe(UsersRepository.usersDisposable)
        return usersLiveData
    }

    fun updateUser(user: User): LiveData<UsersState> {
        userLiveData = UsersRepository.updateUser(getApplication(), user)
        addSubscribe(UsersRepository.updateDisposable)
        return userLiveData
    }
}