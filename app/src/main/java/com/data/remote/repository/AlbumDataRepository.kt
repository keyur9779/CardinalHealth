package com.data.remote.repository

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.annotation.NonNull
import androidx.lifecycle.LiveData
import com.App
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.data.local.dao.AlbumDao
import com.data.local.entity.AlbumEntity
import com.data.remote.ApiService
import com.data.remote.NetworkServerDataApi
import com.data.remote.Resource
import com.view.callbacks.ResponseListener
import retrofit2.Call
import javax.inject.Inject

/**
 * The album repository which has access to local and remote data fetching services

 */
class AlbumDataRepository @Inject internal constructor(private val articleDao: AlbumDao, private val apiService: ApiService) {

    /**
     * This method fetches the popular articles from the service.
     * Once the fetching is done the data is cached to local db so that the app can even work offline
     * @return List of articles
     */
    fun loadPopularArticles(): LiveData<Resource<List<AlbumEntity?>?>> {
        return object :
                NetworkServerDataApi<List<AlbumEntity?>?, List<AlbumEntity?>?>() {
            override fun saveCallResult(item: List<AlbumEntity?>?) {
                if (null != item) {
                    articleDao.saveArticles(item)
                }
            }

            @NonNull
            override fun loadFromDb(): LiveData<List<AlbumEntity?>?> {
                val data =
                        articleDao.loadPopularArticles()
                return data!!
            }

            @NonNull
            override fun createCall(): Call<List<AlbumEntity?>?> {
                return apiService.loadMusicList()
            }
        }.asLiveData
    }

    /**
     * This method fetches the HTML comntent from the url and parses it and fills the model
     * @param url url to be fetched
     * @param responseListener callback
     */
    @SuppressLint("CheckResult")
    fun downloadImage(url: String, responseListener: ResponseListener) {/*
        val articleDetails = ArticleEntity()
        Observable.fromCallable {
            val document = Jsoup.connect(url).get()
            articleDetails.title = document.title()
            articleDetails.content = document.select("p").text()
            false
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result: Boolean? -> responseListener.onSuccess(articleDetails) }
                ) { error: Throwable -> responseListener.onFailure(error.message) }*/


        Glide.with(App.appContext)
                .asBitmap()
                .load(url)
                .into(object : SimpleTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                        responseListener.onSuccess(resource);
                    }

                    override fun onLoadFailed(errorDrawable: Drawable?) {
                        super.onLoadFailed(errorDrawable)
                        responseListener.onFailure("failed to load image as URL not valid or timeout error or network error")

                    }
                });
    }


}