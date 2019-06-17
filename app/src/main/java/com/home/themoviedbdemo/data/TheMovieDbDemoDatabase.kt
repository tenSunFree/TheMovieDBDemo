package com.home.themoviedbdemo.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.home.themoviedbdemo.data.tv.TvDao
import com.home.themoviedbdemo.data.tv.TvData

/**
 * 註解@Database, 用來標識該類是Room的一個Database
 * entities字段, 表示該DataBase綁定的表, 多個表以逗號分開
 * version字段, 表示該DataBase的版本
 * exportSchema字段, 設置為false 可以避免一些錯誤的提示
 */
@Database(entities = [TvData::class], version = 1, exportSchema = false)
abstract class TheMovieDbDemoDatabase : RoomDatabase() {

    abstract fun tvDao(): TvDao

    companion object {
        const val DATABASE_NAME = "the_movie_db_demo_database"
    }
}