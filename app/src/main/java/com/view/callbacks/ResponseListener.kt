package com.view.callbacks

import android.graphics.Bitmap
import com.data.local.entity.AlbumEntity

interface ResponseListener {
    fun onSuccess(data: Bitmap)
    fun onFailure(message: String?)
}