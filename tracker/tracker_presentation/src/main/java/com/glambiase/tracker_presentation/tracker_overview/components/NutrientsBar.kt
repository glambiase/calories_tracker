package com.glambiase.tracker_presentation.tracker_overview.components

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Canvas
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import com.glambiase.core_ui.CarbsColor
import com.glambiase.core_ui.FatsColor
import com.glambiase.core_ui.ProteinsColor

@Composable
fun NutrientsBar(
    carbs: Int,
    proteins: Int,
    fats: Int,
    calories: Int,
    caloriesGoal: Int,
    modifier: Modifier = Modifier
) {
    val background = MaterialTheme.colors.background
    val caloriesOverGoalColor = MaterialTheme.colors.error
    val carbsWidthRatio = remember {
        Animatable(0f)
    }
    val proteinsWidthRatio = remember {
        Animatable(0f)
    }
    val fatsWidthRatio = remember {
        Animatable(0f)
    }

    LaunchedEffect(key1 = carbs) {
        carbsWidthRatio.animateTo(
            targetValue = ((carbs * 4f) / caloriesGoal)
        )
    }
    LaunchedEffect(key1 = proteins) {
        proteinsWidthRatio.animateTo(
            targetValue = ((proteins * 4f) / caloriesGoal)
        )
    }
    LaunchedEffect(key1 = fats) {
        fatsWidthRatio.animateTo(
            targetValue = ((fats * 9f) / caloriesGoal)
        )
    }

    Canvas(
        modifier = modifier
    ) {
        if (calories <= caloriesGoal) {
            val carbsWidth = carbsWidthRatio.value * size.width
            val proteinsWidth = proteinsWidthRatio.value * size.width
            val fatsWidth = fatsWidthRatio.value * size.width
            drawRoundRect(
                color = background,
                size = size,
                cornerRadius = CornerRadius(100f)
            )
            drawRoundRect(
                color = FatsColor,
                size = Size(
                    width = carbsWidth + proteinsWidth + fatsWidth,
                    height = size.height
                ),
                cornerRadius = CornerRadius(100f)
            )
            drawRoundRect(
                color = ProteinsColor,
                size = Size(
                    width = carbsWidth + proteinsWidth,
                    height = size.height
                ),
                cornerRadius = CornerRadius(100f)
            )
            drawRoundRect(
                color = CarbsColor,
                size = Size(
                    width = carbsWidth,
                    height = size.height
                ),
                cornerRadius = CornerRadius(100f)
            )
        } else {
            drawRoundRect(
                color = caloriesOverGoalColor,
                size = size,
                cornerRadius = CornerRadius(100f)
            )
        }
    }
}