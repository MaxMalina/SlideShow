package com.example.maksymg.slideshow

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.VideoView
import com.example.maksymg.slideshow.data.Media
import com.example.maksymg.slideshow.data.MediaType
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var photoViewModel: PhotoViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initAnalogClock()
        initViewFlipper()

        photoViewModel = ViewModelProviders.of(this).get(PhotoViewModel::class.java)

        photoViewModel!!.getAllPhotos().observe(this, Observer { photos: List<Media>? ->
            for(photo in photos!!) {
                if(photo.mediaType == MediaType.IMAGE) {
                    val imageView = ImageView(this)
                    imageView.scaleType = ImageView.ScaleType.FIT_CENTER

                    Picasso.get().load("file:///" + photo.path).into(imageView)
                    viewFlipper.addView(imageView)
                } else {
                    val videoView = VideoView(this)
                    videoView.setVideoPath(photo.path)
                    viewFlipper.addView(videoView)
                }
            }
        })

    }

    private fun initAnalogClock() {
        analogClock.setAutoUpdate(true)
        analogClock.setScale(0.9f)
    }

    private fun initViewFlipper() {
        viewFlipper.inAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        viewFlipper.outAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_out)

        viewFlipper.isAutoStart = true
        viewFlipper.setFlipInterval(3000)
        viewFlipper.startFlipping()
    }
}
