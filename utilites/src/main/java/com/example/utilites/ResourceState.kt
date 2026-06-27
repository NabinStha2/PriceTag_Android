package com.example.utilites

sealed class ResourceState<out T> {
    data object Initial : ResourceState<Nothing>()
    data object Loading : ResourceState<Nothing>()
    data class Success<T>(val data: T) : ResourceState<T>()
    data class Error(val error: String) : ResourceState<Nothing>()
}