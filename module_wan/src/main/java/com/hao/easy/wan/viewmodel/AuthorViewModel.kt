package com.hao.easy.wan.viewmodel

import com.hao.easy.base.extensions.subscribeBy
import com.hao.easy.base.utils.CoroutineUtils
import com.hao.easy.base.viewmodel.BaseViewModel
import com.hao.easy.wan.db.Db
import com.hao.easy.wan.model.Author
import com.hao.easy.wan.repository.Api

open class AuthorViewModel : BaseViewModel() {

    fun getAuthors() {
        CoroutineUtils.io {
            val list = Db.instance().authorDao().queryAll()
            if (list.isEmpty()) {
                Api.getAuthors().subscribeBy({
                    processData(it)
                }).add()
            }
        }
    }

    private fun processData(data: ArrayList<Author>?) {
        if (data == null || data.isEmpty()) {
            return
        }
        var subscribeSort = 10
        var otherIndexSort = 100

        data.forEach {
            when {
                it.id == Author.ID_HONGYANG -> {
                    it.visible = 1
                    it.sort = 1
                }
                it.id == Author.ID_GUOLIN -> {
                    it.visible = 1
                    it.sort = 2
                }
                subscribeSort < 12 -> {
                    it.visible = 1
                    it.sort = subscribeSort++
                }
                else -> {
                    it.visible = 0
                    it.sort = otherIndexSort++
                }
            }
        }

        CoroutineUtils.io {
            Db.instance().authorDao().insert(data)
        }
    }

    fun subscribe(author: Author) {
        author.visible = Author.VISIBLE
        CoroutineUtils.io {
            Db.instance().authorDao().update(author)
        }
    }

    fun cancelSubscribe(author: Author) {
        author.visible = Author.INVISIBLE
        CoroutineUtils.io {
            Db.instance().authorDao().update(author)
        }
    }

    fun update(authors: List<Author>) {
        CoroutineUtils.io {
            Db.instance().authorDao().updateAll(authors)
        }
    }

    fun sort(data: ArrayList<Author>, fromPosition: Int, toPosition: Int): Boolean {
        if (fromPosition !in data.indices
            || toPosition !in data.indices
            || data[fromPosition].isImportant()
            || data[toPosition].isImportant()
        ) {
            return false
        }

        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                swap(data, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                swap(data, i, i - 1)
            }
        }

        return true
    }

    private fun swap(data: ArrayList<Author>, index1: Int, index2: Int) {
        val data1 = data[index1]
        val data2 = data[index2]
        val temp = data1.sort
        data1.sort = data2.sort
        data2.sort = temp
        data[index1] = data2
        data[index2] = data1
    }
}