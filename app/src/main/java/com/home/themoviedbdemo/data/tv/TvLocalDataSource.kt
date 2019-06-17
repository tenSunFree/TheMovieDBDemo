package com.home.themoviedbdemo.data.tv

import com.home.themoviedbdemo.data.DatabaseMaker
import io.reactivex.Single

/**
 * 提供跟local相關的方法
 */
class TvLocalDataSource : TvRepositoryMethod {

    private val dao = DatabaseMaker.db.tvDao()

    /**
     * 取得popular的資料
     */
    override fun getPopularData(): Single<List<TvData>> =
        dao.loadPopular()
            .firstOrError() // 如果為空元素的時候 拋出異常
            .doOnSuccess { if (it.isEmpty()) throw Exception() }

    /**
     * 保存popular的資料
     */
    override fun saveRepositories(list: List<TvData>) = dao.insertAll(list.toMutableList())
}