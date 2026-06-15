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
        fun getInstance(context: Context): UserDatabase {
            return databaseBuilder(
                context,
                UserDatabase::class.java, USER_DATABASE_NAME
            )
                .allowMainThreadQueries()
                .build()
        }

    }

    abstract fun getDataAccessObject(): UserDataAccessObject
}