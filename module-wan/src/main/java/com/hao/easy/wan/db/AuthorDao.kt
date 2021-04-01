package com.hao.easy.wan.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.hao.easy.wan.model.Author

/**
 * @author Yang Shihao
 */
@Dao
interface AuthorDao {

    @Insert
    fun insert(authors: ArrayList<Author>)

    @Update
    fun update(author: Author)

    @Update
    fun updateAll(authors: List<Author>)

    @Query("SELECT * FROM Author WHERE visible in (:visible) ORDER BY sort ASC")
    fun queryByVisible(visible: Int): List<Author>

    @Query("SELECT * FROM Author WHERE visible in (:visible) ORDER BY sort ASC")
    fun queryLiveDataByVisible(visible: Int): LiveData<List<Author>>

    @Query("SELECT * FROM Author")
    fun queryAll(): List<Author>
}