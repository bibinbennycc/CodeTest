package com.codetest.app

import com.codetest.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class WeatherForecastApplication : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out WeatherForecastApplication> {
        return DaggerAppComponent.builder().create(this)
    }
}