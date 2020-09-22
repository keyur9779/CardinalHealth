package com.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.data.local.entity.AlbumEntity

/**
 * File Description
 *
 *
w
 */
@Dao
interface AlbumDao {
    @Query("SELECT * FROM articles")
    fun loadPopularArticles(): LiveData<List<AlbumEntity?>?>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveArticles(articleEntities: List<AlbumEntity?>?)
}