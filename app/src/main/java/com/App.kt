package com

import android.app.Activity
import android.app.Application
import com.AppDependencyInjector.components.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

/**
 * File Description
 *

 */
class App : Application(), HasActivityInjector {
    @kotlin.jvm.JvmField
    @Inject
    var activityDispatchingInjector: DispatchingAndroidInjector<Activity>? = null
    override fun onCreate() {
        super.onCreate()
        setInstance(this)

        initializeComponent()
    }

    private fun initializeComponent() {
        DaggerAppComponent.builder()
                .application(this)
                ?.build()
                ?.inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> {
        return activityDispatchingInjector!!
    }

    companion object {
        lateinit var appContext: App
            private set

        @Synchronized
        private fun setInstance(app: App) {
            appContext = app
        }
    }
}