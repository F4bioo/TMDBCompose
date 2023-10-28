package com.fappslab.core.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fappslab.core.data.local.dao.MovieDao
import com.fappslab.core.data.local.model.MovieEntity

@Database(entities = [MovieEntity::class], exportSchema = false, version = 1)
internal abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
}
