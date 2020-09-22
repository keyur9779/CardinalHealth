package com.data.remote

import com.data.local.entity.AlbumEntity
import retrofit2.Call
import retrofit2.http.GET

/**
 * The APIService interface which will contain the semantics of all the REST calls.

 */
interface ApiService {
    @GET("photos")
    fun loadMusicList(): Call<List<AlbumEntity?>?>
}