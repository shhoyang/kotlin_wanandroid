package com.hao.easy.base.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource

class DataSourceFactory<T>(private var dataLoader: PagedDataLoader<T>) :
    DataSource.Factory<Int, T>() {

    val sourceLiveData = MutableLiveData<PagedDataSource<T>>()

    override fun create(): DataSource<Int, T> {
        val dataSource = PagedDataSource(dataLoader)
        sourceLiveData.postValue(dataSource)
        return dataSource
    }

//    override fun create(): PagedDataSource<T>? {
//        val dataSource = PagedDataSource(dataLoader)
//        sourceLiveData.postValue(dataSource)
//        return dataSource
//    }
}
