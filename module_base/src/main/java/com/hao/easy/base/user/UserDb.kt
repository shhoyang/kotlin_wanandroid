package com.hao.easy.base.user

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hao.easy.base.BaseApplication

/**
 * @author Yang Shihao
 */
@Database(entities = [User::class], version = 2)
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