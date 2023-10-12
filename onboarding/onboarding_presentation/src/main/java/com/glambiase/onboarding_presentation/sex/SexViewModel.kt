package com.glambiase.onboarding_presentation.sex

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glambiase.core.domain.model.Sex
import com.glambiase.core.domain.preferences.Preferences
import com.glambiase.core.navigation.Route
import com.glambiase.core.util.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SexViewModel @Inject constructor(
    private val preferences: Preferences
) : ViewModel() {

    var selectedSex by mutableStateOf<Sex>(Sex.Female)
        private set

    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onSexSelected(sex: Sex) {
        selectedSex = sex
    }

    fun onNextClick() {
        viewModelScope.launch {
            preferences.saveSex(selectedSex)
            _uiEvent.send(UIEvent.Navigate(Route.HEIGHT))
        }
    }
}