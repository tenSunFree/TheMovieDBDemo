package com.home.themoviedbdemo.view.tv

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.MutableLiveData
import com.home.themoviedbdemo.data.tv.TvData
import com.home.themoviedbdemo.data.tv.TvRepository

/**
 * Created by putraxor on 04/06/17.
 */
class TvViewModel(application: Application?) : AndroidViewModel(application!!) {

    companion object {
        const val FILTER_POPULAR = "Popular"
    }

    private val repository = TvRepository()
    private val filterLiveData = MutableLiveData<String>()
    private val resultLiveData =
        TvLiveData(repository) // 提供給isLoading throwable response 監聽
    val isLoadingLiveData = MediatorLiveData<Boolean>() // 提供給View監聽, laoding圖示是否顯示
    val throwableLiveData = MediatorLiveData<Throwable>() // 提供給View監聽, 顯示錯誤訊息
    val responseLiveData = MediatorLiveData<List<TvData>>() // 提供給View監聽, 把資料加載至View

    init {

        /**
         * addSource(filterLiveData): 觀察filterLiveData
         */
        resultLiveData.addSource(filterLiveData) {
            it?.let { filter ->
                resultLiveData.filter = filter // 將String傳入resultLiveData, 以觸發對應的相關方法
            }
        }
        isLoadingLiveData.addSource(resultLiveData) {
            isLoadingLiveData.value = false // 讓SwipeRefreshLayout停止loading動畫
        }
        throwableLiveData.addSource(resultLiveData) {
            it?.second?.let { throwableLiveData.value = it }
        }
        responseLiveData.addSource(resultLiveData) {
            it?.first?.let { data -> responseLiveData.value = data }
        }
    }

    fun setFilter(filterName: String) {
        filterLiveData.value = filterName
        isLoadingLiveData.value = true
    }
}