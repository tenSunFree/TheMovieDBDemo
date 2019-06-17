package com.home.themoviedbdemo.data.tv

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Repository 統一管理請求資料的方法, 不管是從local或者從remote取得
 */
class TvRepository : TvRepositoryMethod {

    private val localDataSource = TvLocalDataSource()
    private val remoteDataSource = TvRemoteDataSource()

    /**
     * 在取得popular的資料時, 會先向remote請求, 如果remote沒有的話, 再向local請求
     * subscribeOn()從事件發出的開端就造成影響, 當你調用subscribeOn()時, 被觀察者 也就是數據源, 就已切換至subscribeOn()所調用的線程中
     * 當observeOn()和subscribeOn()同時存在時, 數據源和操作流程會先處於subscribeOn()所在的線程, 直到調用了observeOn()後, 線程會切換至observeOn()所在的線程
     */
    override fun getPopularData(): Single<List<TvData>> = remoteDataSource.getPopularData()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .doOnSuccess { // 取得remote資料
            Observable.just(it)
                .observeOn(Schedulers.io())
                .subscribe {
                    run {
                        localDataSource.saveRepositories(it) // 將remote資料 保存至local
                    }
                }
        }
        .onErrorResumeNext { // 如果取得remote資料失敗, 則開始取得local資料
            localDataSource.getPopularData()
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess {}
        }
}