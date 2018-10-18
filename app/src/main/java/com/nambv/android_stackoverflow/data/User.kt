package com.nambv.android_stackoverflow.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

@Entity(tableName = "user")
data class User(

        @PrimaryKey
        @SerializedName("user_id") var userId: Int = 0,
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
        @SerializedName("user_type") var userType: String? = "",
        @SerializedName("accept_rate") var acceptRate: Int = 0,
        @SerializedName("location") var location: String? = "",
        @SerializedName("website_url") var websiteUrl: String? = "",
        @SerializedName("link") var link: String? = "",
        @SerializedName("profile_image") var profileImage: String? = "",
        @SerializedName("display_name") var displayName: String? = "",
        var bookmarked: Boolean? = false) : Parcelable {

    constructor(source: Parcel) : this(
            source.readInt(),
            source.readInt(),
            1 == source.readInt(),
            source.readInt(),
            source.readLong(),
            source.readInt(),
            source.readInt(),
            source.readInt(),
            source.readInt(),
            source.readInt(),
            source.readInt(),
            source.readInt(),
            source.readString(),
            source.readInt(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readValue(Boolean::class.java.classLoader) as Boolean?
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(userId)
        writeInt(accountId)
        writeInt((if (isEmployee) 1 else 0))
        writeInt(lastModifiedDate)
        writeLong(lastAccessDate)
        writeInt(reputationChangeYear)
        writeInt(reputationChangeQuarter)
        writeInt(reputationChangeMonth)
        writeInt(reputationChangeWeek)
        writeInt(reputationChangeDay)
        writeInt(reputation)
        writeInt(creationDate)
        writeString(userType)
        writeInt(acceptRate)
        writeString(location)
        writeString(websiteUrl)
        writeString(link)
        writeString(profileImage)
        writeString(displayName)
        writeValue(bookmarked)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<User> = object : Parcelable.Creator<User> {
            override fun createFromParcel(source: Parcel): User = User(source)
            override fun newArray(size: Int): Array<User?> = arrayOfNulls(size)
        }
    }
}