package com.home.themoviedbdemo.view.tv

import android.view.View
import android.widget.TextView
import com.facebook.drawee.view.SimpleDraweeView
import com.home.themoviedbdemo.MovinConfig
import com.home.themoviedbdemo.R
import com.home.themoviedbdemo.base.BaseAdapter
import com.home.themoviedbdemo.base.BaseViewHolder
import com.home.themoviedbdemo.data.tv.TvData

class TvAdapter : BaseAdapter<TvData, TvAdapter.ViewHolder>() {

    override fun getItemViewId(): Int = R.layout.activity_tv_movie_recycler_view_item

    override fun instantiateViewHolder(view: View?): ViewHolder =
        ViewHolder(view)

    class ViewHolder(itemView: View?) : BaseViewHolder<TvData>(itemView!!) {

        private val firstPosition = 0
        private val nameTextView: TextView by lazy { itemView?.findViewById(R.id.nameTextView) as TextView }
        private val overviewTextView: TextView by lazy { itemView?.findViewById(R.id.overviewTextView) as TextView }
        private val imageSimpleDraweeView: SimpleDraweeView by lazy { itemView?.findViewById(R.id.imageSimpleDraweeView) as SimpleDraweeView }
        private val firstPositionView: View by lazy { itemView?.findViewById(R.id.firstPositionView) as View }

        override fun onBind(item: TvData, position: Int) {
            nameTextView.text = item.name
            overviewTextView.text = item.overview
            imageSimpleDraweeView.setImageURI("${MovinConfig.IMG_BASE_URL}${item.posterPath}")
            if (position == firstPosition) {
                firstPositionView.visibility = View.VISIBLE
            } else {
                firstPositionView.visibility = View.GONE
            }
        }
    }
}