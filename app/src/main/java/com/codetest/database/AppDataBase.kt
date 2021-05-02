package com.codetest.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.codetest.feature.model.Location

private const val APP_DATABASE_VERSION = 1

@Database(entities = [Location::class],version = APP_DATABASE_VERSION)
abstract class AppDataBase : RoomDatabase() {
    abstract fun locationDao(): LocationDao
}