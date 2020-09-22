package com.view.callbacks

import com.data.local.entity.AlbumEntity

/**
 * File Description
 *
 *

 */
interface AlbumListCallback {
    fun onAlbumClicked(albumDataEntity: AlbumEntity)
}