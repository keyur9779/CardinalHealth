package com.view.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

/**
 * File Description
 *
 *

 */
abstract class BaseActivity<D : ViewDataBinding?> : AppCompatActivity(), HasSupportFragmentInjector {
    @kotlin.jvm.JvmField
    @Inject
    var fragmentAndroidInjector: DispatchingAndroidInjector<Fragment>? = null
    var dataBinding: D? = null

    @get:LayoutRes
    protected abstract val layoutRes: Int
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentAndroidInjector!!
    }
}