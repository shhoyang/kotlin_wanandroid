package com.hao.easy.base.user

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

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