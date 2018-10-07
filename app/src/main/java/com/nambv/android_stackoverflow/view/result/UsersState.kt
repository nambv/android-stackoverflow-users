package com.nambv.android_stackoverflow.view.result

import com.nambv.android_stackoverflow.data.User

sealed class UsersState {
    object Loading : UsersState()
    data class Data(val users: List<User>) : UsersState()
    data class Error(val throwable: Throwable) : UsersState()
}