package com.home.themoviedbdemo.data.tv

import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.fuel.rx.rxObject
import com.home.themoviedbdemo.MovinConfig
import com.home.themoviedbdemo.ext.log
import io.reactivex.Single

/**
 * 提供跟remote相關的方法
 */
class TvRemoteDataSource : TvRepositoryMethod {

    init {
        FuelManager.instance.basePath = MovinConfig.API_BASE_URL // fuel全局設置
        FuelManager.instance.baseParams = listOf("api_key" to MovinConfig.API_KEY) // 請求參數
    }

    private val suffixUrl = "/tv/popular"

    /**
     * 取得popular的資料
     */
    override fun getPopularData(): Single<List<TvData>> = suffixUrl
        .httpGet().log().rxObject(TvData.ListDeserializer())
        .map { it.component1() ?: throw it.component2() ?: throw Exception() }
}