package com.hao.easy.wechat.model

data class Author(var courseId: Int,
                  var id: Int,
                  var name: String,
                  var order: Int,
                  var parentChapterId: Int,
                  var userControlSetTop: Boolean,
                  var visible: Int)