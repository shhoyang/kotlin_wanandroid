package com.hao.easy.base.user

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import com.hao.easy.base.App

/**
 * @author Yang Shihao
 * @date 2018/9/26
 */
@Database(entities = [User::class], version = 1)
abstract class UserDb : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {

        private var instance: UserDb? = null

        @Synchronized
        fun instance(): UserDb {
            if (instance == null) {
                instance = Room.databaseBuilder(
                        App.instance,
                        UserDb::class.java,
                        "UserDatabase").build()
            }

            return instance!!
        }
    }
}