package com.pivnoydevelopment.cafeapp.core.util

sealed class NetworkResult<out T> {
    data class Success<T>(val data: T): NetworkResult<T>()
    data class Error(val code: Int, val message: String?): NetworkResult<Nothing>()
    object Unauthorized : NetworkResult<Nothing>()
    object NotFound : NetworkResult<Nothing>()
    object LoginAlreadyExists : NetworkResult<Nothing>()
    object BadRequest : NetworkResult<Nothing>()
}