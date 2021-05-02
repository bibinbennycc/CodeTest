package com.codetest.di.module

import com.codetest.feature.view.AddLocationDialogFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilderModule {

    @ContributesAndroidInjector
    abstract fun bindAddLocationDialogFragment(): AddLocationDialogFragment
}