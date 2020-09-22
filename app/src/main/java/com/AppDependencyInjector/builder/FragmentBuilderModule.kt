package com.AppDependencyInjector.builder

import com.view.fragment.AlbumDetailFragment
import com.view.fragment.AlbumListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * This builder provides android injector service to fragments
 *
 */
@Module
abstract class FragmentBuilderModule {
    @ContributesAndroidInjector
    abstract fun contributeArticleListFragment(): AlbumListFragment?

    @ContributesAndroidInjector
    abstract fun contributeArticleDetailFragment(): AlbumDetailFragment?
}