package com.example.maksymg.slideshow.data

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.content.Context
import android.provider.MediaStore

class PhotoRepository(private var context : Context) {

    private var selection = (MediaStore.Files.FileColumns.MEDIA_TYPE + "="
            + MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE
            + " OR "
            + MediaStore.Files.FileColumns.MEDIA_TYPE + "="
            + MediaStore.Files.FileColumns.MEDIA_TYPE_VIDEO)

    private val uri = MediaStore.Files.getContentUri("external")

    fun getAllPhotosPath() : LiveData<List<String>> {
        val cursor = context.contentResolver.query(uri, null, selection, null, MediaStore.Files.FileColumns.DATE_ADDED)
        val allImagesPath = ArrayList<String>()

        while (cursor.moveToNext()) {
            allImagesPath.add(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)))
        }

        val allImagesPathLive = MutableLiveData<List<String>>()
        allImagesPathLive.value = allImagesPath.reversed()
        return allImagesPathLive
    }

}