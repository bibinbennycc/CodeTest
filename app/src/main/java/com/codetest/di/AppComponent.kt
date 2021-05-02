package com.codetest.di

import com.codetest.app.WeatherForecastApplication
import com.codetest.di.module.*
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules=[AndroidSupportInjectionModule::class, AppModule::class, ActivityBuilderModule::class, FragmentBuilderModule::class, NetworkModule::class, ViewModelModule::class])
interface AppComponent : AndroidInjector<WeatherForecastApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<WeatherForecastApplication>()

}