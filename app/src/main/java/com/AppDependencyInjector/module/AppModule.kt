package com.AppDependencyInjector.module

import android.app.Application
import androidx.room.Room
import com.utils.Constants
import com.data.local.AlbumDatabase
import com.data.local.dao.AlbumDao
import com.data.remote.ApiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * The application module which provides app wide instances of various components

 */
@Module(includes = [ViewModelModule::class])
class AppModule {
    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
        okHttpClient.connectTimeout(Constants.CONNECT_TIMEOUT, TimeUnit.SECONDS)
        okHttpClient.readTimeout(Constants.READ_TIMEOUT, TimeUnit.SECONDS)
        okHttpClient.writeTimeout(Constants.WRITE_TIMEOUT, TimeUnit.SECONDS)
        okHttpClient.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        return okHttpClient.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient?): ApiService {
        val retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideArticleDatabase(application: Application?): AlbumDatabase {
        return Room.databaseBuilder(application!!, AlbumDatabase::class.java, "articles.db").build()
    }

    @Provides
    @Singleton
    fun provideArticleDao(articleDatabase: AlbumDatabase): AlbumDao {
        return articleDatabase.articleDao()
    }
}