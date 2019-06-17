package com.home.themoviedbdemo.data.tv

import io.reactivex.Single

/**
 * 提供相關的方法, 供其它類繼承使用
 */
interface TvRepositoryMethod {

    fun getPopularData(): Single<List<TvData>>
    fun saveRepositories(list: List<TvData>) : Unit = Unit
}