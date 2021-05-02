package com.codetest.di.module

import com.codetest.feature.view.WeatherForecastActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilderModule {

    @ContributesAndroidInjector
    abstract fun bindWeatherForecastActivity(): WeatherForecastActivity
}