package com.example.mywallet.data

import okhttp3.ResponseBody

sealed class Response <out R> private constructor() {
    data class Success<out T>(val data: T) : Response<T>()
    data class Error(val message: String?, val errorCode: Int?, val errorBody: ResponseBody?) : Response<Nothing>()
    object Loading : Response<Nothing>()
}
