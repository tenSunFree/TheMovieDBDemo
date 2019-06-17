package com.home.themoviedbdemo.data

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.arch.persistence.room.Room
import android.content.Context
import com.home.themoviedbdemo.data.TheMovieDbDemoDatabase.Companion.DATABASE_NAME
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.atomic.AtomicBoolean

object DatabaseMaker {

    private val isDbCreated = MutableLiveData<Boolean>()
    lateinit var db: TheMovieDbDemoDatabase
    private val initializing = AtomicBoolean(true) // 一個值可以被自動更新的原子工具類

    /**
     * 以單例模式創建一個Database實例
     */
    @SuppressLint("CheckResult")
    fun createDb(context: Context) {
        if (initializing.compareAndSet(true, false).not()) { // compareAndSet(): 它的判斷和更新操作是原子的, 中間不會被其他線程插入, 非常適合多線程情況
            return
        }
        isDbCreated.value = false
        Completable.fromAction {
            db = Room.databaseBuilder(context, TheMovieDbDemoDatabase::class.java, DATABASE_NAME).build() // 獲取Database實例
        }
            .subscribeOn(Schedulers.computation()) // Schedulers.computation(): 用於計算任務, 如事件循環或和回調處理, 不要用於IO操作(IO操作請使用Schedulers.io()): 默認線程數等於處理器的數量
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ isDbCreated.value = true }, { it.printStackTrace() })
    }
}