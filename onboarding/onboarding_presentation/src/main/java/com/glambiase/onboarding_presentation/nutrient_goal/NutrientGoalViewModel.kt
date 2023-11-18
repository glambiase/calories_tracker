package com.glambiase.onboarding_presentation.nutrient_goal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glambiase.core.domain.preferences.Preferences
import com.glambiase.core.domain.use_case.FilterDigitsUseCase
import com.glambiase.core.navigation.Route
import com.glambiase.core.util.UIEvent
import com.glambiase.onboarding_domain.use_case.ValidateNutrientsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NutrientGoalViewModel @Inject constructor(
    private val preferences: Preferences,
    private val filterDigitsUseCase: FilterDigitsUseCase,
    private val validateNutrientsUseCase: ValidateNutrientsUseCase,
) : ViewModel() {

    var state by mutableStateOf(NutrientGoalState())
        private set

    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: NutrientGoalEvent) {
        when (event) {
            is NutrientGoalEvent.OnCarbsRatioEntered -> {
                state = state.copy(carbsRatio = filterDigitsUseCase(event.ratio))
            }
            is NutrientGoalEvent.OnProteinsRatioEntered -> {
                state = state.copy(proteinsRatio = filterDigitsUseCase(event.ratio))
            }
            is NutrientGoalEvent.OnFatsRatioEntered -> {
                state = state.copy(fatsRatio = filterDigitsUseCase(event.ratio))
            }
            NutrientGoalEvent.OnNextClick -> {
                val validationResult = validateNutrientsUseCase(
                    carbsRatio = state.carbsRatio,
                    proteinsRatio = state.proteinsRatio,
                    fatsRatio = state.fatsRatio
                )
                when (validationResult) {
                    is ValidateNutrientsUseCase.Result.Success -> {
                        preferences.saveCarbsRatio(validationResult.carbsRatio)
                        preferences.saveProteinsRatio(validationResult.proteinsRatio)
                        preferences.saveFatsRatio(validationResult.fatsRatio)
                        viewModelScope.launch {
                            _uiEvent.send(UIEvent.Navigate(Route.TRACKER_OVERVIEW))
                        }
                    }
                    is ValidateNutrientsUseCase.Result.Error-> {
                        viewModelScope.launch {
                            _uiEvent.send(UIEvent.ShowSnackbar(validationResult.message))
                        }
                    }
                }
            }
        }
    }
}