package com.nambv.android_stackoverflow.data

import com.google.gson.annotations.SerializedName

data class BadgeCounts(
        @SerializedName("bronze") var bronze: Int = 0,
        @SerializedName("silver") var silver: Int = 0,
        @SerializedName("gold") var gold: Int = 0
)