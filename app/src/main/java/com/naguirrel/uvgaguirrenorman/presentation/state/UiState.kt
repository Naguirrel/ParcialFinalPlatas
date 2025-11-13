package com.naguirrel.uvgaguirrenorman.presentation.state

sealed class UiState<out T> {
    object Loading : UiState<Nothing>()
    data class Success<T>(
        val data: T,
        val fromLocal: Boolean,
        val lastUpdate: Long?
    ) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}
