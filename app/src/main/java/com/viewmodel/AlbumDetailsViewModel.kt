package com.viewmodel

import android.graphics.Bitmap
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.data.remote.repository.AlbumDataRepository
import com.model.AlbumImageModel
import com.view.callbacks.ResponseListener
import javax.inject.Inject

/**
 * ArticleDetails view model
 *
 *

 */
class AlbumDetailsViewModel @Inject internal constructor(private val albumRepository: AlbumDataRepository?) : ViewModel() {
    lateinit var url: String
    var albumEntityMutableLiveData = MutableLiveData<AlbumImageModel>()

    fun getImageFromURL() {
        albumRepository?.downloadImage(url, object : ResponseListener {
            override fun onSuccess(data: Bitmap) {
                val dataBitmap = AlbumImageModel(data, "", true)
                albumEntityMutableLiveData.value = dataBitmap
            }

            override fun onFailure(message: String?) {
                // Send event to UI to show thw error
                val dataBitmap = AlbumImageModel(null, message!!, false)

                albumEntityMutableLiveData.value = dataBitmap

            }
        })
    }

}