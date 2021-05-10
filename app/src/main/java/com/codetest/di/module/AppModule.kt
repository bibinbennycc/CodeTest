package com.codetest.di.module

import android.content.Context
import androidx.room.Room
import com.codetest.app.WeatherForecastApplication
import com.codetest.database.AppDataBase
import com.codetest.feature.repository.AppRepository
import com.codetest.feature.repository.MainRepository
import com.codetest.network.ApiService
import dagger.Module
import dagger.Provides

const val APP_DATABASE_NAME = "LocationDatabase"

@Module
class AppModule {

    @Provides
    fun getAppContext(application: WeatherForecastApplication): Context = application

    @Provides
    fun getAppDataBase(application: WeatherForecastApplication): AppDataBase {
        return Room.databaseBuilder(application.applicationContext, AppDataBase::class.java, APP_DATABASE_NAME).build()
    }

    @Provides
    fun getMainRepository(appDataBase: AppDataBase, apiService: ApiService): AppRepository {
        return MainRepository(apiService, appDataBase) as AppRepository
    }
}