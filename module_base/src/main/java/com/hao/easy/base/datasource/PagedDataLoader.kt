package com.hao.easy.base.datasource

import androidx.paging.PageKeyedDataSource

interface PagedDataLoader<T> {

    fun refresh(callback: PageKeyedDataSource.LoadInitialCallback<Int, T>)

    fun loadMore(key: Int, callback: PageKeyedDataSource.LoadCallback<Int, T>)
}