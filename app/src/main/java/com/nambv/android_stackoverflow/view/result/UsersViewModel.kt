package com.nambv.android_stackoverflow.view.result

import android.app.Application
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.github.ajalt.timberkt.Timber
import com.nambv.android_stackoverflow.data.User
import com.nambv.android_stackoverflow.repository.UsersRepository
import com.nambv.android_stackoverflow.view.base.BaseViewModel
import io.reactivex.disposables.Disposable

class UsersViewModel(application: Application) : BaseViewModel(application) {

    private var usersRepository: UsersRepository = UsersRepository(getApplication(), userApi, userDao)

    private var usersDisposable: Disposable? = null
    private var updateDisposable: Disposable? = null

    fun fetchUsers(page: Int, pageSize: Int, bookmarked: Boolean?): LiveData<UsersState> {

        val usersLiveData = MutableLiveData<UsersState>()

        if (page > 1)
            usersLiveData.setValue(UsersState.LoadMore)
        else
            usersLiveData.setValue(UsersState.Refreshing)

        usersDisposable?.let { if (usersDisposable?.isDisposed == false) usersDisposable?.dispose() }

        usersDisposable = usersRepository.fetchUsers(page, pageSize, bookmarked)
                .subscribe(
                        {
                            usersLiveData.postValue(UsersState.Data(it))
                        },
                        {
                            usersLiveData.postValue(UsersState.Error(it))
                        })


        addSubscribe(usersDisposable)
        return usersLiveData
    }

    fun updateUser(user: User): LiveData<UsersState> {

        val userLiveData = MutableLiveData<UsersState>()

        updateDisposable = usersRepository.updateUser(user)
                .subscribe(
                        {
                            Timber.w { "bookmarked: ${user.bookmarked}" }
                            userLiveData.setValue(UsersState.Updated)
                        },
                        {
                            userLiveData.setValue(UsersState.Error(it))
                        })


        addSubscribe(updateDisposable)
        return userLiveData
    }
}