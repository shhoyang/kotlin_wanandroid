package com.hao.library.viewmodel.datasource

import androidx.paging.PageKeyedDataSource

interface PagedDataLoader<T> {

    fun refresh(callback: PageKeyedDataSource.LoadInitialCallback<Int, T>)

    fun loadMore(key: Int, callback: PageKeyedDataSource.LoadCallback<Int, T>)
}