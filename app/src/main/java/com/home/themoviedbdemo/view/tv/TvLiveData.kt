package com.home.themoviedbdemo.view.tv

import android.arch.lifecycle.MediatorLiveData
import com.home.themoviedbdemo.data.tv.TvData
import com.home.themoviedbdemo.data.tv.TvRepository
import com.home.themoviedbdemo.view.tv.TvViewModel.Companion.FILTER_POPULAR
import io.reactivex.disposables.Disposable

/**
 * MediatorLiveData類就是個自定義LiveData, 可以觀察其他LiveData對象並且回調
 */
class TvLiveData(private val repository: TvRepository) : MediatorLiveData<Pair<List<TvData>?, Throwable?>>() {

    private var disposable: Disposable? = null

    /**
     * 透過傳進來的filter, 執行對應的行為
     *
     */
    var filter: String? = null
        set(value) {
            value?.let {
                when (it) {
                    FILTER_POPULAR -> {
                        disposable = repository
                            .getPopularData()
                            .subscribe { data, error ->
                                this@TvLiveData.value = Pair(data, error) // 傳值給自己, 通知正在監聽自己的MediatorLiveData
                            }
                    }
                }
            }
            field = value
        }

    override fun onInactive() {
        super.onInactive()
        if (disposable?.isDisposed?.not() == true) {
            disposable?.dispose()
        }
    }
}