package com.glambiase.tracker_presentation.tracker_overview.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.glambiase.tracker_presentation.components.UnitDisplay

@Composable
fun NutrientsBarInfo(
    value: Int,
    caloriesGoal: Int,
    nutrientType: String,
    color: Color,
    modifier: Modifier = Modifier,
    strokeWidth: Dp = 8.dp
) {
    val background = MaterialTheme.colors.background
    val caloriesOverGoalColor = MaterialTheme.colors.error
    val angleRatio = remember {
        Animatable(0f)
    }

    LaunchedEffect(key1 = value) {
        angleRatio.animateTo(
            targetValue = if (caloriesGoal > 0) {
                value / caloriesGoal.toFloat()
            } else {
                0f
            },
            animationSpec = tween(
                durationMillis = 600
            )
        )
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        ) {
            drawArc(
                color = if (value <= caloriesGoal) background else caloriesOverGoalColor,
                startAngle = 0f,
                sweepAngle = 360f,
                useCenter = false,
                size = size,
                style = Stroke(
                    width = strokeWidth.toPx(),
                    cap = StrokeCap.Round
                )
            )
            if (value <= caloriesGoal) {
                drawArc(
                    color = color,
                    startAngle = 90f,
                    sweepAngle = 360f * angleRatio.value,
                    useCenter = false,
                    size = size,
                    style = Stroke(
                        width = strokeWidth.toPx(),
                        cap = StrokeCap.Round
                    )
                )
            }
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            UnitDisplay(
                amount = value,
                unit = stringResource(id = com.glambiase.core.R.string.grams),
                amountColor = if (value <= caloriesGoal) MaterialTheme.colors.onPrimary else caloriesOverGoalColor,
                unitColor = if (value <= caloriesGoal) MaterialTheme.colors.onPrimary else caloriesOverGoalColor
            )
            Text(
                text = nutrientType,
                color = if (value <= caloriesGoal) MaterialTheme.colors.onPrimary else caloriesOverGoalColor,
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Light
            )
        }
    }
}