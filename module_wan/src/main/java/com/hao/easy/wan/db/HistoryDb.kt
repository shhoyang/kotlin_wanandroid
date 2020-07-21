package com.hao.easy.wan.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hao.easy.base.BaseApplication
import com.hao.easy.wan.model.HotWord

/**
 * @author Yang Shihao
 * @date 2018/9/26
 */
@Database(entities = [HotWord::class], version = 1, exportSchema = false)
abstract class HistoryDb : RoomDatabase() {

    abstract fun historyDao(): HistoryDao

    companion object {

        private var instance: HistoryDb? = null

        @Synchronized
        fun instance(): HistoryDb {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    BaseApplication.instance,
                    HistoryDb::class.java,
                    "WanAndroidHistory"
                ).build()
            }

            return instance!!
        }
    }
}