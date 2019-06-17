package com.home.themoviedbdemo.base

import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log

@Suppress("LeakingThis")
abstract class BaseLifecycleActivity<T : AndroidViewModel> : AppCompatActivity(), LifecycleOwner {

    abstract val viewModelClass: Class<T>
    protected lateinit var viewModel: T
    private val registry = LifecycleRegistry(this)

    override fun getLifecycle(): LifecycleRegistry = registry

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        Log.d("more", "BaseLifecycleActivity, onCreate")
        viewModel = ViewModelProviders.of(this).get(viewModelClass)
    }
}
