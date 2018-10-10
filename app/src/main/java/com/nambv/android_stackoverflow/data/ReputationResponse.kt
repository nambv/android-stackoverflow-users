package com.nambv.android_stackoverflow.data

import com.google.gson.annotations.SerializedName

data class ReputationResponse(
        @SerializedName("items") var items: List<Reputation> = listOf(),
        @SerializedName("has_more") var hasMore: Boolean = false,
        @SerializedName("quota_max") var quotaMax: Int = 0,
        @SerializedName("quota_remaining") var quotaRemaining: Int = 0
)