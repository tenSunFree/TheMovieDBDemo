package com.home.themoviedbdemo.data.tv

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Flowable

/**
 * 提供一系列query, insert, udpate, delete..等的函數
 */
@Dao
interface TvDao {

    @Query("SELECT * FROM movies ORDER BY popularity DESC")
    fun loadPopular(): Flowable<List<TvData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(list: MutableList<TvData>)
}