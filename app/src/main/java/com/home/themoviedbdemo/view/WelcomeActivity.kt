package com.home.themoviedbdemo.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import com.home.themoviedbdemo.R
import com.home.themoviedbdemo.data.tv.TvRepository
import com.home.themoviedbdemo.view.tv.TvActivity
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : AppCompatActivity() {

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        setContentView(R.layout.activity_welcome)
        initializeView()
        leftImageView.postDelayed({loadFirstData()},2000)
    }

    private fun initializeView() {
        startAnimation(leftImageView, 350)
        startAnimation(centerImageView, 540)
        startAnimation(rightImageView, 255)
        startAnimation(prepareLabelTextView, 1000)
        startPopcornAnimation()
    }

    private fun startAnimation(view: View, duration: Long) {
        val anim = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        anim.duration = duration
        view.startAnimation(anim)
    }

    private fun startPopcornAnimation() {
        val anim = AnimationUtils.loadAnimation(this, R.anim.fade_in_zoom)
        circleView.startAnimation(anim)
    }

    /**
     * 請求資料, 如果能取得資料 才能跳轉至TvActivity
     */
    @SuppressLint("CheckResult")
    private fun loadFirstData() {
        val repository = TvRepository()
        repository.getPopularData()
                .subscribe { data, error ->
                    if (data != null) {
                        startActivity(Intent(this, TvActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Application error ${error.message}", Toast.LENGTH_SHORT).show()
                    }
                }
    }
}
