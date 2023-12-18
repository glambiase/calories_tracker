package com.glambiase.tracker_domain.use_case

import com.glambiase.core.domain.model.ActivityLevel
import com.glambiase.core.domain.model.Goal
import com.glambiase.core.domain.model.Sex
import com.glambiase.core.domain.model.UserInfo
import com.glambiase.core.domain.preferences.Preferences
import com.glambiase.tracker_domain.model.MealType
import com.glambiase.tracker_domain.model.TrackedFood
import kotlin.math.roundToInt

class CalculateMealNutrientsUseCase(
    private val preferences: Preferences
) {
    operator fun invoke(
        trackedFoodList: List<TrackedFood>
    ): Result {
        val nutrients = trackedFoodList
            .groupBy { trackedFood ->
                trackedFood.mealType
            }
            .mapValues { entry ->
                val mealType = entry.key
                val food = entry.value
                MealNutrients(
                    calories = food.sumOf { it.calories },
                    carbs = food.sumOf { it.carbs },
                    fats = food.sumOf { it.fats },
                    proteins = food.sumOf { it.proteins },
                    mealType = mealType
                )
            }

        val userInfo = preferences.loadUserInfo()
        val caloriesGoal = dailyCaloriesRequirement(userInfo)

        return Result(
            caloriesGoal = caloriesGoal,
            carbsGoal = (caloriesGoal * userInfo.carbsRatio / CALORIES_PER_GRAM_OF_CARBS).roundToInt(),
            fatsGoal = (caloriesGoal * userInfo.fatsRatio / CALORIES_PER_GRAM_OF_FATS).roundToInt(),
            proteinsGoal = (caloriesGoal * userInfo.proteinsRatio / CALORIES_PER_GRAM_OF_PROTEINS).roundToInt(),
            totalCalories = nutrients.values.sumOf { it.calories },
            totalCarbs = nutrients.values.sumOf { it.carbs },
            totalFats = nutrients.values.sumOf { it.fats },
            totalProteins = nutrients.values.sumOf { it.proteins },
            mealNutrientsByMealType = nutrients
        )
    }

    data class MealNutrients(
        val calories: Int,
        val carbs: Int,
        val fats: Int,
        val proteins: Int,
        val mealType: MealType
    )

    data class Result(
        val caloriesGoal: Int,
        val carbsGoal: Int,
        val fatsGoal: Int,
        val proteinsGoal: Int,
        val totalCalories: Int,
        val totalCarbs: Int,
        val totalFats: Int,
        val totalProteins: Int,
        val mealNutrientsByMealType: Map<MealType, MealNutrients>
    )

    private fun dailyCaloriesRequirement(userInfo: UserInfo): Int {
        val activityFactor = when (userInfo.activityLevel) {
            ActivityLevel.Low -> 1.2f
            ActivityLevel.Medium -> 1.3f
            ActivityLevel.High -> 1.4f
        }
        val caloriesExtra = when (userInfo.goal) {
            Goal.LoseWeight -> -500
            Goal.KeepWeight -> 0
            Goal.GainWeight -> 500
        }
        return (bmr(userInfo) * activityFactor + caloriesExtra).roundToInt()
    }

    private fun bmr(userInfo: UserInfo): Int =
        when (userInfo.sex) {
            Sex.Female -> (665.09f + 9.56f * userInfo.weight + 1.84f * userInfo.height - 4.67 * userInfo.age).roundToInt()
            Sex.Male -> (66.47f + 13.75f * userInfo.weight + 5f * userInfo.height - 6.75f * userInfo.age).roundToInt()
        }

    companion object {
        private const val CALORIES_PER_GRAM_OF_CARBS = 4f
        private const val CALORIES_PER_GRAM_OF_FATS = 9f
        private const val CALORIES_PER_GRAM_OF_PROTEINS = 4f
    }
}