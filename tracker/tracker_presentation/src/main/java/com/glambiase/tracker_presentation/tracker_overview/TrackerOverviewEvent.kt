package com.glambiase.tracker_presentation.tracker_overview

import com.glambiase.tracker_domain.model.TrackedFood

sealed class TrackerOverviewEvent {

    object OnPreviousDayClick : TrackerOverviewEvent()
    object OnNextDayClick : TrackerOverviewEvent()
    data class OnToggleMealClick(val meal: Meal) : TrackerOverviewEvent()
    data class OnAddFoodClick(val meal: Meal) : TrackerOverviewEvent()
    data class OnDeleteTrackedFoodClick(val trackedFood: TrackedFood) : TrackerOverviewEvent()
}