package com.AppDependencyInjector.components

import android.app.Application
import com.App
import com.AppDependencyInjector.builder.ActivityBuilderModule
import com.AppDependencyInjector.module.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

/**
 * The main application component which initializes all the dependent modules

 */
@Singleton
@Component(modules = [AppModule::class, AndroidInjectionModule::class, ActivityBuilderModule::class])
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application?): Builder?
        fun build(): AppComponent?
    }

    fun inject(nyTimesApp: App?)
}