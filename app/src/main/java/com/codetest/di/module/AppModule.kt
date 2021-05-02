package com.codetest.di.module

import android.content.Context
import androidx.room.Room
import com.codetest.app.WeatherForecastApplication
import com.codetest.database.AppDataBase
import dagger.Module
import dagger.Provides

const val  APP_DATABASE_NAME = "LocationDatabase"

@Module
 class AppModule{

    @Provides
     fun getAppContext(application: WeatherForecastApplication): Context = application

    @Provides
    fun getAppDataBase(application: WeatherForecastApplication): AppDataBase {
        return Room.databaseBuilder(application.applicationContext, AppDataBase::class.java,APP_DATABASE_NAME).build()
    }
}