package com.glambiase.onboarding_presentation.height

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glambiase.core.domain.preferences.Preferences
import com.glambiase.core.domain.use_case.FilterDigitsUseCase
import com.glambiase.core.navigation.Route
import com.glambiase.core.util.UIEvent
import com.glambiase.core.util.UIText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HeightViewModel @Inject constructor(
    private val preferences: Preferences,
    private val filterDigitsUseCase: FilterDigitsUseCase
) : ViewModel() {

    var selectedHeight by mutableStateOf("160")
        private set

    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onHeightEntered(height: String) {
        if (height.length <= 3) selectedHeight = filterDigitsUseCase(height)
    }

    fun onNextClick() {
        viewModelScope.launch {
            val heightNumber = selectedHeight.toIntOrNull() ?: run {
                _uiEvent.send(
                    UIEvent.ShowSnackbar(
                        UIText.StringResource(com.glambiase.core.R.string.error_height_cannot_be_empty)
                    )
                )
                return@launch
            }
            preferences.saveHeight(heightNumber)
            _uiEvent.send(UIEvent.Navigate(Route.WEIGHT))
        }
    }
}