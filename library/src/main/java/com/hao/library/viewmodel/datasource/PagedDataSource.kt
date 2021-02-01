package com.hao.library.viewmodel.datasource

import androidx.paging.PageKeyedDataSource

class PagedDataSource<T>(private var dataLoader: PagedDataLoader<T>?) :
    PageKeyedDataSource<Int, T>() {

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, T>
    ) {
        dataLoader?.refresh(callback)
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        dataLoader?.loadMore(params.key, callback)
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
    }
}