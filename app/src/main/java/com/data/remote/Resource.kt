package com.data.remote

/**
 * A generic class that holds a value with its loading status.
 * @param <T>

</T> */
data class Resource<T> private constructor(val status: Status, val data: T, val message: String) {


    companion object {
        fun <T> success(data: T): Resource<T> {
            return Resource(Status.SUCCESS, data!!, "")
        }

        fun <T> error(msg: String, data: T): Resource<T> {
            return Resource(Status.ERROR, data!!, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data!!, "")
        }
    }

}