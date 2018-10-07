package com.nambv.android_stackoverflow.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "user")
data class User(

        @PrimaryKey
        @SerializedName("user_id") var userId: Int = 0,

//        @SerializedName("badge_counts") var badgeCounts: BadgeCounts = BadgeCounts(),
        @SerializedName("account_id") var accountId: Int = 0,
        @SerializedName("is_employee") var isEmployee: Boolean = false,
        @SerializedName("last_modified_date") var lastModifiedDate: Int = 0,
        @SerializedName("last_access_date") var lastAccessDate: Long = 0,
        @SerializedName("reputation_change_year") var reputationChangeYear: Int = 0,
        @SerializedName("reputation_change_quarter") var reputationChangeQuarter: Int = 0,
        @SerializedName("reputation_change_month") var reputationChangeMonth: Int = 0,
        @SerializedName("reputation_change_week") var reputationChangeWeek: Int = 0,
        @SerializedName("reputation_change_day") var reputationChangeDay: Int = 0,
        @SerializedName("reputation") var reputation: Int = 0,
        @SerializedName("creation_date") var creationDate: Int = 0,
        @SerializedName("user_type") var userType: String = "",
        @SerializedName("accept_rate") var acceptRate: Int = 0,
        @SerializedName("location") var location: String = "",
        @SerializedName("website_url") var websiteUrl: String = "",
        @SerializedName("link") var link: String = "",
        @SerializedName("profile_image") var profileImage: String = "",
        @SerializedName("display_name") var displayName: String = ""
)