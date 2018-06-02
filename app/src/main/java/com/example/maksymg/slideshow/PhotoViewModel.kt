package com.example.maksymg.slideshow

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.example.maksymg.slideshow.data.Media
import com.example.maksymg.slideshow.data.PhotoRepository

class PhotoViewModel(application: Application) : AndroidViewModel(application) {
    private var photos: LiveData<List<Media>>? = null
    private val couponRepository = PhotoRepository(application.applicationContext)

    fun getAllPhotos(): LiveData<List<Media>> {
        if (photos == null) {
            photos = couponRepository.getAllPhotosPath()
        }
        return photos as LiveData<List<Media>>
    }
}
