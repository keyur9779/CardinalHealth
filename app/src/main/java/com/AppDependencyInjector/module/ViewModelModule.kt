package com.AppDependencyInjector.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.viewmodel.AlbumDetailsViewModel
import com.viewmodel.AlbumListViewModel
import com.viewmodel.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Allows us to inject dependencies via constructor injection
 *
 *

 */
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(AlbumListViewModel::class)
    abstract fun bindsArticleListViewModel(articleListViewModel: AlbumListViewModel?): ViewModel?

    @Binds
    @IntoMap
    @ViewModelKey(AlbumDetailsViewModel::class)
    abstract fun bindsArticleDetailsiewModel(articleDetailsViewModel: AlbumDetailsViewModel?): ViewModel?

    @Binds
    abstract fun bindsViewModelFactory(viewModelFactory: ViewModelFactory?): ViewModelProvider.Factory?
}