package com.glambiase.tracker_domain.use_case

import com.glambiase.tracker_domain.model.MealType
import com.glambiase.tracker_domain.model.TrackableFood
import com.glambiase.tracker_domain.model.TrackedFood
import com.glambiase.tracker_domain.repository.TrackerRepository
import java.time.LocalDate
import kotlin.math.roundToInt

class TrackFoodUseCase(
    private val repository: TrackerRepository
) {
    suspend operator fun invoke(
        trackableFood: TrackableFood,
        amount: Int,
        mealType: MealType,
        date: LocalDate
    ) {
        repository.insertTrackedFood(
            TrackedFood(
                name = trackableFood.name,
                imageUrl = trackableFood.imageUrl,
                calories = trackableFood.caloriesPer100g,
                carbs = (trackableFood.carbsPer100g * amount / 100f).roundToInt(),
                fats = (trackableFood.fatsPer100g * amount / 100f).roundToInt(),
                proteins = (trackableFood.proteinsPer100g * amount / 100f).roundToInt(),
                mealType = mealType,
                amount = amount,
                date = date
            )
        )
    }
}