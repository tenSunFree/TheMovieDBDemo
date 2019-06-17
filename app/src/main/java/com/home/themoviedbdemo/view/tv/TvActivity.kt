package com.home.themoviedbdemo.view.tv

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.widget.SwipeRefreshLayout
import com.home.themoviedbdemo.R
import com.home.themoviedbdemo.base.BaseLifecycleActivity
import com.home.themoviedbdemo.data.tv.TvData
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter
import kotlinx.android.synthetic.main.activity_tv.*

class TvActivity : BaseLifecycleActivity<TvViewModel>(),
    SwipeRefreshLayout.OnRefreshListener {

    override val viewModelClass = TvViewModel::class.java
    private val adapter = TvAdapter()

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        setContentView(R.layout.activity_tv)
        setSupportActionBar(toolbar)
        initializeRecyclerView()
        initializeSwipeRefreshLayout()
        initializeCollapsingToolbarLayout()
        if (bundle == null) viewModel.setFilter(TvViewModel.FILTER_POPULAR)
    }

    private fun initializeCollapsingToolbarLayout() {
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.CollapsingToolbarLayoutTitleColor)
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsingToolbarLayoutTitleColor)
    }

    private fun initializeSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener(this)
        swipeRefreshLayout.setColorSchemeResources(
            R.color.colorAccent,
            R.color.colorPrimary,
            R.color.colorPrimaryDark
        )
    }

    private fun initializeRecyclerView() {
        recyclerView.setHasFixedSize(true) // 當不是瀑布流時, 設置為true 可以避免重複的增刪造成的浪費資源
        val animateAdapter = AlphaInAnimationAdapter(adapter) // 列表的動畫效果
        animateAdapter.setDuration(1000)
        recyclerView.adapter = animateAdapter
    }

    override fun onResume() {
        super.onResume()
        observeLiveData()
    }

    /**
     * Observer live data
     */
    private fun observeLiveData() {
        viewModel.isLoadingLiveData.observe(this, Observer<Boolean> {
            it?.let { isRefreshing -> swipeRefreshLayout.isRefreshing = isRefreshing }
        })
        viewModel.responseLiveData.observe(this, Observer<List<TvData>> {
            it?.let { data -> adapter.dataSource = data }
        })
        viewModel.throwableLiveData.observe(this, Observer<Throwable> {
            it?.let { Snackbar.make(recyclerView, "請求資料失敗", Snackbar.LENGTH_LONG).show() }
        })
    }

    override fun onRefresh() {
        viewModel.setFilter(TvViewModel.FILTER_POPULAR)
    }
}
