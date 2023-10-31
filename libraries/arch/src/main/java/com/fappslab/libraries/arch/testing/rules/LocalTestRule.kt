package com.fappslab.libraries.arch.testing.rules

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.test.core.app.ApplicationProvider
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@VisibleForTesting
class LocalTestRule : TestWatcher() {

    private val context = ApplicationProvider.getApplicationContext<Context>()
    private var database: RoomDatabase? = null

    override fun finished(description: Description) {
        super.finished(description)
        database?.close()
    }

    @Suppress("NON_PUBLIC_CALL_FROM_PUBLIC_INLINE")
    inline fun <reified Database : RoomDatabase> createTestDatabase(): Database {
        database = Room.inMemoryDatabaseBuilder(
            context.applicationContext,
            Database::class.java
        ).allowMainThreadQueries().build()

        return database as Database
    }
}
