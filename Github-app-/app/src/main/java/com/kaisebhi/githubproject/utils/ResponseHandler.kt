package com.kaisebhi.githubproject.utils

sealed class ResponseHandler<T>(val responseList: T? = null, val errorMessage: String? = null) {
    class Loading<T>: ResponseHandler<T>()
    class Success<T>(responseList: T?): ResponseHandler<T>(responseList = responseList)
    class Error<T>(errorMessage: String?): ResponseHandler<T>(errorMessage = errorMessage)
}