package com.home.themoviedbdemo

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import com.home.themoviedbdemo.data.DatabaseMaker

class Application : Application() {
    override fun onCreate() {
        super.onCreate()
        DatabaseMaker.createDb(this)
        Fresco.initialize(this)
    }
}
