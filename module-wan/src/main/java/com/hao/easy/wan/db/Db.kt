package com.hao.easy.wan.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hao.easy.base.BaseApplication
import com.hao.easy.wan.model.Author
import com.hao.easy.wan.model.HotWord

/**
 * @author Yang Shihao
 */
@Database(entities = [Author::class, HotWord::class], version = 1)
abstract class Db : RoomDatabase() {

    abstract fun authorDao(): AuthorDao

    abstract fun historyDao(): HistoryDao

    companion object {

        private var instance: Db? = null

        @Synchronized
        fun instance(): Db {
            @Synchronized
            if (instance == null) {
                instance = Room.databaseBuilder(
                    BaseApplication.instance,
                    Db::class.java,
                    "WanAndroidDB"
                ).build()
            }

            return instance!!
        }
    }
}