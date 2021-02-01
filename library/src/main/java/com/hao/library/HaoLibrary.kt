package com.hao.library

import android.content.Context
import com.hao.library.extensions.notNullSingleValue

/**
 * @author Yang Shihao
 */

object HaoLibrary {

    var context by notNullSingleValue<Context>()
    var CONFIG by notNullSingleValue<HaoLibraryConfig>()

    fun init(context: Context, libraryConfig: HaoLibraryConfig) {
        this.context = context
        this.CONFIG = libraryConfig
    }
}