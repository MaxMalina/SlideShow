package com.example.maksymg.slideshow

import android.Manifest
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.VideoView
import com.example.maksymg.slideshow.data.Media
import com.example.maksymg.slideshow.data.MediaType
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ActivityCompat.OnRequestPermissionsResultCallback {

    private var photoViewModel: PhotoViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initAnalogClock()
        initViewFlipper()

        requestExternalStoragePermission()
    }

    private fun requestExternalStoragePermission() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 0)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if(grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
            photoViewModel = ViewModelProviders.of(this).get(PhotoViewModel::class.java)

            photoViewModel!!.getAllPhotos().observe(this, Observer { photos: List<Media>? ->
                for (photo in photos!!) {
                    var mediaView = View(this)
                    when (photo.mediaType) {
                        MediaType.IMAGE -> {
                            mediaView = ImageView(this)
                            mediaView.scaleType = ImageView.ScaleType.FIT_CENTER
                            Picasso.get().load("file:///" + photo.path).into(mediaView)
                        }

                        MediaType.VIDEO -> {
                            mediaView = VideoView(this)
                            mediaView.setVideoPath(photo.path)
                            mediaView.start()
                        }
                    }
                    viewFlipper.addView(mediaView)
                }
            })
        }
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
