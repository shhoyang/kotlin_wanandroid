package com.hao.easy.base.user

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

/**
 * @author Yang Shihao
 */
@Dao
interface UserDao {

    @Insert
    fun insert(user: User)

    @Query("SELECT * FROM User WHERE username in (:username)")
    fun query(username: String): User?

    @Query("SELECT * FROM User LIMIT 1")
    fun lastUser(): LiveData<User?>

    @Delete
    fun delete(user: User)

    @Query("DELETE FROM User")
    fun deleteAll()
}