package com.nambv.android_stackoverflow.data

import com.google.gson.annotations.SerializedName

data class UsersResponse(
        @SerializedName("items") var users: List<User> = listOf(),
        @SerializedName("has_more") var hasMore: Boolean = false,
        @SerializedName("quota_max") var quotaMax: Int = 0,
        @SerializedName("quota_remaining") var quotaRemaining: Int = 0
)