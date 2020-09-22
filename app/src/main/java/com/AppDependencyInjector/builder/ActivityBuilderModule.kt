package com.AppDependencyInjector.builder

import com.view.activity.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * The module which provides the android injection service to Activities.

 */
@Module
abstract class ActivityBuilderModule {
    @ContributesAndroidInjector(modules = [FragmentBuilderModule::class])
    abstract fun mainActivity(): MainActivity?
}