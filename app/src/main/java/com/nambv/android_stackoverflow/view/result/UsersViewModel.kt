package com.nambv.android_stackoverflow.view.result

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.nambv.android_stackoverflow.data.User
import com.nambv.android_stackoverflow.repository.UsersRepository

class UsersViewModel(application: Application) : AndroidViewModel(application) {

    private var usersLiveData: LiveData<UsersState>? = null
    private var userLiveData: LiveData<UsersState>? = null

    fun fetchUsers(page: Int, pageSize: Int, bookmarked: Boolean?): LiveData<UsersState> {
        usersLiveData = UsersRepository.fetchUsers(getApplication(), page, pageSize, bookmarked)
        return usersLiveData!!
    }

    fun updateUser(user: User): LiveData<UsersState> {
        userLiveData = UsersRepository.updateUser(getApplication(), user)
        return userLiveData!!
    }
}