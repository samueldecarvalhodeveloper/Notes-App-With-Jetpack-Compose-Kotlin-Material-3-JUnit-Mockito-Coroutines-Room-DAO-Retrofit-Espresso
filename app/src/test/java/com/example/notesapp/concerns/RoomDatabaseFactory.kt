package com.example.notesapp.concerns

import android.content.Context
import androidx.room.Room.databaseBuilder
import androidx.room.Room.inMemoryDatabaseBuilder
import androidx.room.RoomDatabase
import androidx.test.core.app.ApplicationProvider

object RoomDatabaseFactory {
    fun <T : RoomDatabase> getInstance(roomDatabaseClass: Class<T>): T {
        val context = ApplicationProvider.getApplicationContext<Context>()

        return inMemoryDatabaseBuilder(context, roomDatabaseClass)
            .allowMainThreadQueries()
            .build()
    }

    fun <T : RoomDatabase> getInstance(roomDatabaseClass: Class<T>, name: String?): T {
        val context = ApplicationProvider.getApplicationContext<Context>()

        return databaseBuilder(context, roomDatabaseClass, name)
            .allowMainThreadQueries()
            .build()
    }
}