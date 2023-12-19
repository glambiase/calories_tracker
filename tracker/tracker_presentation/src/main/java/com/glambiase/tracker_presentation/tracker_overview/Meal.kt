package com.glambiase.tracker_presentation.tracker_overview

import androidx.annotation.DrawableRes
import com.glambiase.core.util.UIText
import com.glambiase.tracker_domain.model.MealType
import com.glambiase.core.R

data class Meal(
    val name: UIText,
    @DrawableRes val drawable: Int,
    val mealType: MealType,
    val calories: Int = 0,
    val carbs: Int = 0,
    val fats: Int = 0,
    val proteins: Int = 0,
    val isExpanded: Boolean = false
)

val defaultMeals = listOf(
    Meal(
        name = UIText.StringResource(R.string.breakfast),
        drawable = R.drawable.ic_breakfast,
        mealType = MealType.Breakfast
    ),
    Meal(
        name = UIText.StringResource(R.string.lunch),
        drawable = R.drawable.ic_lunch,
        mealType = MealType.Lunch
    ),
    Meal(
        name = UIText.StringResource(R.string.dinner),
        drawable = R.drawable.ic_dinner,
        mealType = MealType.Dinner
    ),
    Meal(
        name = UIText.StringResource(R.string.snacks),
        drawable = R.drawable.ic_snack,
        mealType = MealType.Snack
    )
)