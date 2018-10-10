package com.nambv.android_stackoverflow.data

import com.google.gson.annotations.SerializedName

data class Reputation(
        @SerializedName("reputation_history_type") var reputationHistoryType: String = "",
        @SerializedName("reputation_change") var reputationChange: Int = 0,
        @SerializedName("post_id") var postId: Int = 0,
        @SerializedName("creation_date") var creationDate: Int = 0,
        @SerializedName("user_id") var userId: Int = 0
)