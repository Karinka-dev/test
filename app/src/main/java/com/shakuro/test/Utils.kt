package com.shakuro.test


sealed class DataState<out R> {
    data class Success<out T>(val data: T) : DataState<T>()
    data class Error(val exception: Exception) : DataState<Nothing>()
    object Loading : DataState<Nothing>()
}

sealed class MainStateEvent {
    object GetUsersEvent : MainStateEvent()
    object ClearPage : MainStateEvent()
    object AddPage : MainStateEvent()
}