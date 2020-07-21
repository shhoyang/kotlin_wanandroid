package com.hao.easy.wan.viewmodel

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.hao.easy.base.extensions.subscribeBy
import com.hao.easy.base.viewmodel.BaseViewModel
import com.hao.easy.wan.db.HistoryDb
import com.hao.easy.wan.model.HotWord
import com.hao.easy.wan.repository.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SearchViewModel : BaseViewModel() {

    val hotWordLiveData = MutableLiveData<ArrayList<HotWord>>()

    val historyLiveData = MutableLiveData<ArrayList<HotWord>>()

    fun getHotWords() {
        Api.getHotWords().subscribeBy({
            hotWordLiveData.value = it
        }).add()
    }

    fun search(content: String?) {
        GlobalScope.launch {

            val dao = HistoryDb.instance().historyDao()
            if (!TextUtils.isEmpty(content)) {
                dao.deleteByName(content!!)
                dao.insert(HotWord(null, content))
            }

            val list = dao.queryAll()
            withContext(Dispatchers.Main) {
                historyLiveData.value = list as ArrayList<HotWord>
            }
        }
    }

    fun deleteAll() {
        GlobalScope.launch {
            HistoryDb.instance().historyDao().deleteAll()
            withContext(Dispatchers.Main) {
                historyLiveData.value = ArrayList()
            }
        }
    }
}