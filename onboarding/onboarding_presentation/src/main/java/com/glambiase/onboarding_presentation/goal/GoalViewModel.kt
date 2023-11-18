package com.glambiase.onboarding_presentation.goal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glambiase.core.domain.model.Goal
import com.glambiase.core.domain.preferences.Preferences
import com.glambiase.core.navigation.Route
import com.glambiase.core.util.UIEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoalViewModel @Inject constructor(
    private val preferences: Preferences
) : ViewModel() {

    var selectedGoal by mutableStateOf<Goal>(Goal.KeepWeight)
        private set

    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onGoalSelected(goal: Goal) {
        selectedGoal = goal
    }

    fun onNextClick() {
        viewModelScope.launch {
            preferences.saveGoal(selectedGoal)
            _uiEvent.send(UIEvent.Navigate(Route.NUTRIENT_GOAL))
        }
    }
}