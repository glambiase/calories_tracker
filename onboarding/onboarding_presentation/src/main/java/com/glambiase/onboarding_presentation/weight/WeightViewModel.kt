package com.glambiase.onboarding_presentation.weight

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glambiase.core.R
import com.glambiase.core.domain.preferences.Preferences
import com.glambiase.core.navigation.Route
import com.glambiase.core.util.UIEvent
import com.glambiase.core.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeightViewModel @Inject constructor(
    private val preferences: Preferences
) : ViewModel() {

    var selectedWeight by mutableStateOf("80.0")
        private set

    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onWeightEntered(weight: String) {
        if (weight.length <= 6) selectedWeight = weight
    }

    fun onNextClick() {
        viewModelScope.launch {
            val weightNumber = selectedWeight.toFloatOrNull() ?: run {
                _uiEvent.send(
                    UIEvent.ShowSnackbar(
                        UiText.StringResource(R.string.error_weight_cannot_be_empty)
                    )
                )
                return@launch
            }
            preferences.saveWeight(weightNumber)
            _uiEvent.send(UIEvent.Navigate(Route.ACTIVITY))
        }
    }
}