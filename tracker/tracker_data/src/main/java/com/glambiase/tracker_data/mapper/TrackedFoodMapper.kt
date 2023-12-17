package com.glambiase.tracker_data.mapper

import com.glambiase.tracker_data.local.entity.TrackedFoodEntity
import com.glambiase.tracker_domain.model.MealType
import com.glambiase.tracker_domain.model.TrackedFood
import java.time.LocalDate

fun TrackedFoodEntity.toTrackedFood(): TrackedFood {
    return TrackedFood(
        name = name,
        imageUrl = imageUrl,
        calories = calories,
        carbs = carbs,
        fats = fats,
        proteins = proteins,
        mealType = MealType.fromString(type),
        amount = amount,
        date = LocalDate.of(year, month, dayOfMonth),
        id = id
    )
}

fun TrackedFood.toTrackedFoodEntity(): TrackedFoodEntity {
    return TrackedFoodEntity(
        name = name,
        imageUrl = imageUrl,
        calories = calories,
        carbs = carbs,
        fats = fats,
        proteins = proteins,
        type = mealType.name,
        amount = amount,
        dayOfMonth = date.dayOfMonth,
        month = date.monthValue,
        year = date.year,
        id = id
    )
}