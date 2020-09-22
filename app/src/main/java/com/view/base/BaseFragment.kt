package com.view.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.inflate
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

/**
 * File Description
 *
 *

 */
abstract class BaseFragment<V : ViewModel?, D : ViewDataBinding?> : Fragment() {
    @kotlin.jvm.JvmField
    @Inject
    var viewModelFactory: ViewModelProvider.Factory? = null
    protected var viewModel: V? = null
    protected var dataBinding: D? = null
    protected abstract fun getViewModel(): Class<V>?

    @get:LayoutRes
    protected abstract val layoutRes: Int
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)[getViewModel()!!]
    }


}