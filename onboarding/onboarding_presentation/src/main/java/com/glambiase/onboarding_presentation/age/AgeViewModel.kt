package com.glambiase.onboarding_presentation.age

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glambiase.core.domain.preferences.Preferences
import com.glambiase.core.domain.use_case.FilterDigitsUseCase
import com.glambiase.core.util.UIEvent
import com.glambiase.core.R
import com.glambiase.core.navigation.Route
import com.glambiase.core.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AgeViewModel @Inject constructor(
    private val preferences: Preferences,
    private val filterDigitsUseCase: FilterDigitsUseCase
) : ViewModel() {

    var selectedAge by mutableStateOf("25")
        private set

    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onAgeEntered(age: String) {
        if (age.length <= 3) selectedAge = filterDigitsUseCase(age)
    }

    fun onNextClick() {
        viewModelScope.launch {
            val ageNumber = selectedAge.toIntOrNull() ?: run {
                _uiEvent.send(
                    UIEvent.ShowSnackbar(
                        UiText.StringResource(R.string.error_age_cant_be_empty)
                    )
                )
                return@launch
            }
            preferences.saveAge(ageNumber)
            _uiEvent.send(UIEvent.Navigate(Route.SEX))
        }
    }
}