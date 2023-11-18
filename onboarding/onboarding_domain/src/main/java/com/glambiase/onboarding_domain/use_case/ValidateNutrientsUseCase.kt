package com.glambiase.onboarding_domain.use_case

import com.glambiase.core.util.UIText
import com.glambiase.core.R

class ValidateNutrientsUseCase {
    operator fun invoke(
        carbsRatio: String,
        proteinsRatio: String,
        fatsRatio: String
    ): Result {
        val carbs = carbsRatio.toIntOrNull()
        val proteins = proteinsRatio.toIntOrNull()
        val fats = fatsRatio.toIntOrNull()

        return when {
            listOf(carbs, proteins, fats).any { it == null } -> {
                Result.Error(message = UIText.StringResource(R.string.error_invalid_values))
            }
            listOfNotNull(carbs, proteins, fats).sumOf { it } != 100 -> {
                Result.Error(message = UIText.StringResource(R.string.error_not_100_percent))
            }
            else -> {
                Result.Success(
                    carbsRatio = (carbs ?: 0) / 100f,
                    proteinsRatio = (proteins ?: 0) / 100f,
                    fatsRatio = (fats ?: 0) / 100f
                )
            }
        }
    }

    sealed class Result {
        data class Success(
            val carbsRatio: Float,
            val proteinsRatio: Float,
            val fatsRatio:Float
        ) : Result()
        data class Error(val message: UIText) : Result()
    }
}