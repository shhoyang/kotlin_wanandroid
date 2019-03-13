package com.hao.easy.base.user

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

/**
 * @author Yang Shihao
 * @date 2018/9/26
 */
@Dao
interface UserDao {

    @Insert
    fun insert(user: User)

    @Query("SELECT * FROM User WHERE username in (:username)")
    fun queryUser(username: String): User?

    @Delete
    fun delete(user: User)
}