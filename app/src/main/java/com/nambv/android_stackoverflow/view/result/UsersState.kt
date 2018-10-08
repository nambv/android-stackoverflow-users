package com.nambv.android_stackoverflow.view.result

import com.nambv.android_stackoverflow.data.User

sealed class UsersState {

    object Refreshing : UsersState()
    object LoadMore: UsersState()
    object Updating: UsersState()
    object Updated: UsersState()

    data class Data(val users: List<User>) : UsersState()
    data class Error(val throwable: Throwable) : UsersState()
}