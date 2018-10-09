package com.nambv.android_stackoverflow.data.local.dao

import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.IGNORE
import com.nambv.android_stackoverflow.data.User
import io.reactivex.Maybe

@Dao
interface UserDao {

    @Query("SELECT * FROM user WHERE user.bookmarked = :bookmarked")
    fun searchUserList(bookmarked: Boolean): Maybe<List<User>>

    @Query("SELECT * FROM user LIMIT :limit OFFSET :offset")
    fun getUserList(limit: Int, offset: Int): Maybe<List<User>>

    @Query("SELECT * FROM user WHERE user.userId = :id")
    fun getUser(id: Int): Maybe<User>

    @Insert(onConflict = IGNORE)
    fun insert(user: User)

    @Insert(onConflict = IGNORE)
    fun insert(list: List<User>)

    @Update
    fun update(user: User)

    @Delete
    fun deleteUser(user: User)

    @Query("DELETE FROM user WHERE userId = :id")
    fun deleteUser(id: Int)

    @Query("DELETE FROM user")
    fun deleteAll()
}