package com.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.data.local.entity.AlbumEntity
import com.data.remote.Resource
import com.data.remote.repository.AlbumDataRepository
import javax.inject.Inject

/**
 * Article List view model
 *
 *

 */
class AlbumListViewModel @Inject constructor(articleRepository: AlbumDataRepository) : ViewModel() {
    val albumListData: LiveData<Resource<List<AlbumEntity?>?>> = articleRepository.loadPopularArticles()

}