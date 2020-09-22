package com.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

/**
 * File Description
 *
 *

 */
@Entity(tableName = "articles")
@Serializable
data class AlbumEntity(@PrimaryKey
                         val id: Int,

                       val title: String,

                       val url: String,

                       val thumbnailUrl: String
)