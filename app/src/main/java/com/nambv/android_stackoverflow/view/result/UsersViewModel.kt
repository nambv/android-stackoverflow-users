package com.nambv.android_stackoverflow.view.result

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.nambv.android_stackoverflow.data.User
import com.nambv.android_stackoverflow.repository.UsersRepository

class UsersViewModel(application: Application) : AndroidViewModel(application) {

    private var usersLiveData: MutableLiveData<UsersState>? = null
    private var userLiveData: MutableLiveData<UsersState>? = null

    fun fetchUsers(page: Int, pageSize: Int, bookmarked: Boolean?): MutableLiveData<UsersState> {
        usersLiveData = UsersRepository.fetchUsers(getApplication(), page, pageSize, bookmarked)
        return usersLiveData as MutableLiveData<UsersState>
    }

    fun updateUser(user: User): MutableLiveData<UsersState> {
        userLiveData = UsersRepository.updateUser(getApplication(), user)
        return userLiveData!!
    }
}