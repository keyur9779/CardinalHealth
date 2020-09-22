package com.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.data.local.dao.AlbumDao
import com.data.local.entity.AlbumEntity

/**
 * File Description
 *
 *

 */
@Database(entities = [AlbumEntity::class], version = 1, exportSchema = false)
abstract class AlbumDatabase : RoomDatabase() {
    abstract fun articleDao(): AlbumDao
}