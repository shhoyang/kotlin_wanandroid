package com.hao.easy.base.user

import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * @author Yang Shihao
 * @date 2018/11/25
 */
@Entity
data class User(
    @PrimaryKey var id: Int,
    var username: String,
    var token: String,
    var icon: String,
    var email: String,
    var subscribe: String,
)
