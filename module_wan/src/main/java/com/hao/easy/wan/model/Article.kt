package com.hao.easy.wan.model

import com.hao.easy.base.adapter.BaseItem

class Article(var author: String,
              var niceDate: String,
              var link: String,
              var projectLink: String,
              var envelopePic: String,
              var collect: Boolean,
              var originId: Int) : BaseItem() {
    var title: String = ""
        get() {
            return if (null == field || "" == field) {
                ""
            } else {
                field.replace("amp;", "")
            }
        }

    var desc: String = ""
        get() {
            return if (null == field || "" == field) {
                ""
            } else {
                field.replace("amp;", "")
            }
        }
}