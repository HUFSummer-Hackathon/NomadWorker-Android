package com.comjeong.nomadworker.common

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    object Empty : UiState<Nothing>()
    data class Success<out T>(val data: List<T>) : UiState<T>()
    data class Error(val error: String? = null) : UiState<Nothing>()
}
