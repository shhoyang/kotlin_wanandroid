package com.hao.easy.wan.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Author(
    @PrimaryKey(autoGenerate = false)
    var id: Int,
    var name: String,
    var sort: Int,
    var visible: Int
) {
    fun isImportant(): Boolean {
        return id == ID_HONGYANG || id == ID_GUOLIN
    }

    fun isVisible(): Boolean {
        return visible == VISIBLE
    }

    companion object {
        const val ID_HONGYANG = 408
        const val ID_GUOLIN = 409

        const val VISIBLE = 1
        const val INVISIBLE = 0
    }
}