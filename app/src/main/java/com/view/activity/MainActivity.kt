package com.view.activity

import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import com.test.R
import com.test.databinding.ActivityMainBinding
import com.utils.FragmentUtils
import com.view.base.BaseActivity
import com.view.fragment.AlbumListFragment

class MainActivity : BaseActivity<ActivityMainBinding?>() {
    override val layoutRes: Int
        get() = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, layoutRes)
        FragmentUtils.replaceFragment(this, AlbumListFragment.newInstance(), R.id.fragContainer, false, FragmentUtils.TRANSITION_NONE)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return false
    }
}