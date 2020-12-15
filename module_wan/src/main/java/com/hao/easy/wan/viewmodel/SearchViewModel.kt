package com.hao.easy.wan.viewmodel

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.hao.easy.base.extensions.subscribeBy
import com.hao.easy.base.viewmodel.BaseViewModel
import com.hao.easy.wan.db.Db
import com.hao.easy.wan.model.HotWord
import com.hao.easy.wan.repository.Api
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SearchViewModel : BaseViewModel() {

    val hotWordLiveData = MutableLiveData<ArrayList<HotWord>>()

    fun getHotWords() {
        Api.getHotWords().subscribeBy({
            hotWordLiveData.value = it
        }).add()
    }

    fun insert(content: String?) {
        GlobalScope.launch {
            val dao = Db.instance().historyDao()
            if (!TextUtils.isEmpty(content)) {
                dao.deleteByName(content!!)
                dao.insert(HotWord(null, content))
            }
//            withContext(Dispatchers.Main) {
//
//            }
        }
    }

    fun deleteAll() {
        GlobalScope.launch {
            Db.instance().historyDao().deleteAll()
        }
    }
}