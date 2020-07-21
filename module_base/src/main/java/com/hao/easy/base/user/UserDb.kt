package com.hao.easy.base.user

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hao.easy.base.BaseApplication

/**
 * @author Yang Shihao
 * @date 2018/9/26
 */
@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserDb : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {

        private var instance: UserDb? = null

        @Synchronized
        fun instance(): UserDb {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    BaseApplication.instance,
                    UserDb::class.java,
                    "WanAndroid"
                ).build()
            }

            return instance!!
        }
    }
}