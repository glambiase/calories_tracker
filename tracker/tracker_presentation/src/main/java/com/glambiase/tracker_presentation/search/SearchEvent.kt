package com.glambiase.tracker_presentation.search

import com.glambiase.tracker_domain.model.MealType
import com.glambiase.tracker_domain.model.TrackableFood
import java.time.LocalDate

sealed class SearchEvent {
    data class OnQueryChange(val query: String) : SearchEvent()
    data class OnFoodAmountChange(val food: TrackableFood, val amount: String) : SearchEvent()
    data class OnToggleTrackableFood(val food: TrackableFood) : SearchEvent()
    data class OnTrackFoodClick(val food: TrackableFood, val mealType: MealType, val date: LocalDate) : SearchEvent()
    data class OnSearchFocusChange(val isFocused: Boolean) : SearchEvent()
    object OnSearch : SearchEvent()
}