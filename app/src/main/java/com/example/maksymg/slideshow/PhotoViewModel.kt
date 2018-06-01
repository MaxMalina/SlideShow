package com.example.maksymg.slideshow

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.example.maksymg.slideshow.data.PhotoRepository

class PhotoViewModel(application: Application) : AndroidViewModel(application) {
    private var photos: LiveData<List<String>>? = null
    private val couponRepository = PhotoRepository(application.applicationContext)

    fun getAllPhotos(): LiveData<List<String>> {
        if (photos == null) {
            photos = couponRepository.getAllPhotosPath()
        }
        return photos as LiveData<List<String>>
    }
}
