package com.glambiase.core.util

sealed class UIEvent {
    data class Navigate(val route: String): UIEvent()
    object NavigateUp: UIEvent()
    data class ShowSnackbar(val message: UiText): UIEvent()
}