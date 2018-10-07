package com.nambv.android_stackoverflow.view.result

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import com.nambv.android_stackoverflow.repository.UsersRepository

class UsersViewModel(application: Application) : AndroidViewModel(application) {

    private var usersLiveData: MutableLiveData<UsersState>? = null

    fun fetchUsers(): MutableLiveData<UsersState> {
        if (null == usersLiveData)
            usersLiveData = UsersRepository.fetchUsers(getApplication())
        return usersLiveData as MutableLiveData<UsersState>
    }
}