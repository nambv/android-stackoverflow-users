package com.nambv.android_stackoverflow.data.local.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.IGNORE
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import com.nambv.android_stackoverflow.data.User
import io.reactivex.Maybe

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getUserList(): Maybe<List<User>>

    @Query("SELECT * FROM user WHERE user.userId = :id")
    fun getUser(id: String): Maybe<User>

    @Insert(onConflict = IGNORE)
    fun insert(user: User)

    @Insert(onConflict = IGNORE)
    fun insert(list: List<User>)

    @Insert(onConflict = REPLACE)
    fun update(user: User)

    @Delete
    fun deleteUser(user: User)

    @Query("DELETE FROM user WHERE userId = :id")
    fun deleteUser(id: String)

    @Query("DELETE FROM user")
    fun deleteAll()
}