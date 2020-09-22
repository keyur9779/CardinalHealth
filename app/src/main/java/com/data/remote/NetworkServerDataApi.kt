package com.data.remote

import android.annotation.SuppressLint
import android.os.AsyncTask
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.google.gson.stream.MalformedJsonException
import com.App
import com.test.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

/**
 * This class act as the decider to cache the response/ fetch from the service always

 */
abstract class NetworkServerDataApi<T, V> @MainThread protected constructor() {
    private val result = MediatorLiveData<Resource<T>>()

    /**
     * This method fetches the data from remoted service and save it to local db
     * @param dbSource - Database source
     */
    private fun fetchFromNetwork(dbSource: LiveData<T>) {
        result.addSource(dbSource) { newData: T -> result.setValue(Resource.loading(newData)) }
        createCall().enqueue(object : Callback<V> {
            override fun onResponse(call: Call<V>, response: Response<V>) {
                result.removeSource(dbSource)
                saveResultAndReInit(response.body())
            }

            override fun onFailure(call: Call<V>, t: Throwable) {
                result.removeSource(dbSource)
                result.addSource(dbSource) { newData: T -> result.setValue(Resource.error(getCustomErrorMessage(t), newData)) }
            }
        })
    }

    private fun getCustomErrorMessage(error: Throwable): String {
        return if (error is SocketTimeoutException) {
            App.appContext.getString(R.string.requestTimeOutError)
        } else if (error is MalformedJsonException) {
            App.appContext.getString(R.string.responseMalformedJson)
        } else if (error is IOException) {
            App.appContext.getString(R.string.networkError)
        } else if (error is HttpException) {
            error.response()!!.message()
        } else {
            App.appContext.getString(R.string.unknownError)
        }
    }

    @SuppressLint("StaticFieldLeak")
    @MainThread
    private fun saveResultAndReInit(response: V?) {
        object : AsyncTask<Void?, Void?, Void?>() {


            override fun onPostExecute(aVoid: Void?) {
                result.addSource(loadFromDb()) { newData: T? -> if (null != newData) result.setValue(Resource.Companion.success(newData)) }
            }

            override fun doInBackground(vararg params: Void?): Void? {
                saveCallResult(response)
                return null
            }
        }.execute()
    }

    @WorkerThread
    protected abstract fun saveCallResult(item: V?)

    @MainThread
    private fun shouldFetch(): Boolean {
        return true
    }

    @MainThread
    protected abstract fun loadFromDb(): LiveData<T>

    @MainThread
    protected abstract fun createCall(): Call<V>
    val asLiveData: LiveData<Resource<T>>
        get() = result

    init {
        //result.value = Resource.loading(null)

        // Always load the data from DB  so that we have
        val dbSource = loadFromDb()

        // Fetch the data from network and add it to the resource
        result.addSource(dbSource) { _: T ->
            result.removeSource(dbSource)
            if (shouldFetch()) {
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(dbSource) { newData: T? -> if (null != newData) result.value = Resource.success(newData) }
            }
        }
    }
}