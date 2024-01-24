package com.glambiase.tracker_presentation.tracker_overview

import com.glambiase.tracker_domain.model.TrackedFood
import java.time.LocalDate

data class TrackerOverviewState(
    val totalCalories: Int = 0,
    val totalCarbs: Int = 0,
    val totalFats: Int = 0,
    val totalProteins: Int = 0,
    val caloriesGoal: Int = 0,
    val carbsGoal: Int = 0,
    val fatsGoal: Int = 0,
    val proteinsGoal: Int = 0,
    val date: LocalDate = LocalDate.now(),
    val trackedFoodList: List<TrackedFood> = emptyList(),
    val meals: List<Meal> = defaultMeals
)