package com.glambiase.tracker_presentation.tracker_overview

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glambiase.core.domain.preferences.Preferences
import com.glambiase.core.navigation.Route
import com.glambiase.core.util.UIEvent
import com.glambiase.tracker_domain.use_case.CalculateMealNutrientsUseCase
import com.glambiase.tracker_domain.use_case.TrackerUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrackerOverviewViewModel @Inject constructor(
    preferences: Preferences,
    private val trackerUseCases: TrackerUseCases
) : ViewModel() {

    var state by mutableStateOf(TrackerOverviewState())
        private set

    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var getFoodByDateJob: Job? = null

    init {
        preferences.saveShouldShowOnboarding(shouldShowOnboarding = false)
    }

    fun onEvent(event: TrackerOverviewEvent) {
        when (event) {
            TrackerOverviewEvent.OnPreviousDayClick -> {
                state = state.copy(date = state.date.minusDays(1))
                updateFoodState()
            }
            TrackerOverviewEvent.OnNextDayClick -> {
                state = state.copy(date = state.date.plusDays(1))
                updateFoodState()
            }
            is TrackerOverviewEvent.OnToggleMealClick -> {
                state = state.copy(
                    meals = state.meals.map {
                        if (it.name == event.meal.name) it.copy(isExpanded = !it.isExpanded) else it
                    }
                )
            }
            is TrackerOverviewEvent.OnAddFoodClick -> {
                viewModelScope.launch {
                    _uiEvent.send(
                        UIEvent.Navigate(
                            route = Route.SEARCH
                                    + "/${event.meal.mealType.name}"
                                    + "/${state.date.dayOfMonth}"
                                    + "/${state.date.monthValue}"
                                    + "/${state.date.year}"
                        )
                    )
                }
            }
            is TrackerOverviewEvent.OnDeleteTrackedFoodClick -> {
                viewModelScope.launch {
                    trackerUseCases.deleteFoodUseCase(event.trackedFood)
                    updateFoodState()
                }
            }
        }
    }

    private fun updateFoodState() {
        getFoodByDateJob?.cancel()
        getFoodByDateJob = trackerUseCases.getFoodByDateUseCase(state.date)
            .onEach { trackedFoodList ->
                trackerUseCases.calculateMealNutrientsUseCase(trackedFoodList).run {
                    state = state.copy(
                        totalCalories = totalCalories,
                        totalCarbs = totalCarbs,
                        totalFats = totalFats,
                        totalProteins = totalProteins,
                        carbsGoal = carbsGoal,
                        fatsGoal = fatsGoal,
                        proteinsGoal = proteinsGoal,
                        trackedFoodList = trackedFoodList,
                        meals = state.meals.map { meal ->
                            mealNutrientsByMealType.getOrDefault(
                                key = meal.mealType,
                                defaultValue = CalculateMealNutrientsUseCase.MealNutrients(
                                    calories = 0,
                                    carbs = 0,
                                    fats = 0,
                                    proteins = 0,
                                    mealType = meal.mealType
                                )
                            ).run {
                                meal.copy(
                                    calories = calories,
                                    carbs = carbs,
                                    fats = fats,
                                    proteins = proteins
                                )
                            }
                        }
                    )
                }
            }
            .launchIn(viewModelScope)
    }
}