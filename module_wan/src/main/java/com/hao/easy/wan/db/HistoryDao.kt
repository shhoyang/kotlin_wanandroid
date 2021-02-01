package com.hao.easy.wan.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.hao.easy.wan.model.HotWord

/**
 * @author Yang Shihao
 */
@Dao
interface HistoryDao {

    @Insert
    fun insert(hotWord: HotWord)

    @Query("SELECT * FROM HotWord WHERE name in (:name)")
    fun query(name: String): HotWord?

    @Query("SELECT * FROM HotWord")
    fun queryAll(): LiveData<List<HotWord>>

    @Delete
    fun delete(hotWord: HotWord)

    @Query("DELETE FROM HotWord WHERE name in (:name)")
    fun deleteByName(name: String)

    @Query("DELETE FROM HotWord")
    fun deleteAll()
}