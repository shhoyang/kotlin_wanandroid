package com.hao.easy.base.datasource

import android.arch.paging.PageKeyedDataSource

class PagedDataSource<T>(private var dataLoader: PagedDataLoader<T>?) : PageKeyedDataSource<Int, T>() {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, T>) {
        dataLoader?.loadInitial(params, callback)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        dataLoader?.loadAfter(params, callback)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
    }
}