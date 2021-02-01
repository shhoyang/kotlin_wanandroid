package com.hao.library.viewmodel.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource

class DataSourceFactory<T>(private var dataLoader: PagedDataLoader<T>) :
    DataSource.Factory<Int, T>() {

    private val sourceLiveData = MutableLiveData<PagedDataSource<T>>()

    override fun create(): DataSource<Int, T> {
        val dataSource = PagedDataSource(dataLoader)
        sourceLiveData.postValue(dataSource)
        return dataSource
    }

    fun invalidate() {
        sourceLiveData.value?.invalidate()
    }
}
