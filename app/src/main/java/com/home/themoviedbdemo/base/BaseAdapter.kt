package com.home.themoviedbdemo.base

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

abstract class BaseAdapter<D, VH : BaseViewHolder<D>> : RecyclerView.Adapter<VH>() {

    var dataSource: List<D> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = dataSource.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(getItemViewId(), parent, false)
        return instantiateViewHolder(view)
    }

    abstract fun getItemViewId(): Int // 提供layout的id, 比如 activity_tv_movie_recycler_view_item

    abstract fun instantiateViewHolder(view: View?): VH

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(getItem(position), position)
    }

    private fun getItem(position: Int) = dataSource[position]

    private fun getLastPosition() = dataSource.size - 1
}