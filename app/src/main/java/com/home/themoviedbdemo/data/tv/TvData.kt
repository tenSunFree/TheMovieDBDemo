package com.home.themoviedbdemo.data.tv

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.github.kittinunf.fuel.core.ResponseDeserializable
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import com.home.themoviedbdemo.ext.getResultJson

@Entity(tableName = "movies")
open class TvData {

    @PrimaryKey
    @SerializedName("id")
    var id: Int? = null
    @SerializedName("original_name")
    var originalName: String? = null
    @SerializedName("name")
    var name: String? = null
    @SerializedName("popularity")
    var popularity: Double? = null
    @SerializedName("vote_count")
    var voteCount: Int? = null
    @SerializedName("first_air_date")
    var firstAirDate: String? = null
    @SerializedName("backdrop_path")
    var backdropPath: String? = null
    @SerializedName("original_language")
    var originalLanguage: String? = null
    @SerializedName("vote_average")
    var voteAverage: Int? = null
    @SerializedName("overview")
    var overview: String? = null
    @SerializedName("poster_path")
    var posterPath: String? = null

    /**
     * 將取得的json資料, 轉換成List<TvData>的格式
     */
    class ListDeserializer : ResponseDeserializable<List<TvData>> {
        override fun deserialize(content: String): List<TvData> =
            Gson().fromJson<List<TvData>>(content.getResultJson(), object : TypeToken<List<TvData>>() {}.type)
    }
}
