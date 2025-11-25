package com.example.notesapp.data.local_data_source.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import com.example.notesapp.constants.data.UserDataConstants.USER_DATABASE_NAME
import com.example.notesapp.constants.data.UserDataConstants.USER_DATABASE_VERSION
import com.example.notesapp.data.local_data_source.data_access_objects.UserDataAccessObject
import com.example.notesapp.data.local_data_source.entities.UserEntity

@Database(entities = [UserEntity::class], version = USER_DATABASE_VERSION)
abstract class UserDatabase : RoomDatabase() {
    companion object {
        private lateinit var instance: UserDatabase

        fun getInstance(context: Context): UserDatabase {
            if (this::instance.isInitialized.not()) {
                instance = databaseBuilder(
                    context,
                    UserDatabase::class.java, USER_DATABASE_NAME
                )
                    .allowMainThreadQueries()
                    .build()
            }

            return instance
        }

    }

    abstract fun getDataAccessObject(): UserDataAccessObject
}