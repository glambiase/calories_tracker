package com.glambiase.onboarding_presentation.nutrient_goal

sealed class NutrientGoalEvent {
    data class OnCarbsRatioEntered(val ratio: String) : NutrientGoalEvent()
    data class OnProteinsRatioEntered(val ratio: String) : NutrientGoalEvent()
    data class OnFatsRatioEntered(val ratio: String) : NutrientGoalEvent()
    object OnNextClick : NutrientGoalEvent()
}