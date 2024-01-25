package com.glambiase.tracker_presentation.tracker_overview.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.glambiase.core_ui.LocalSpacing
import com.glambiase.tracker_domain.model.TrackedFood
import com.glambiase.core.R
import com.glambiase.tracker_presentation.components.NutrientInfo

@Composable
fun TrackedFoodItem(
    trackedFood: TrackedFood,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val spacing = LocalSpacing.current
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clip(RoundedCornerShape(5.dp))
            .padding(spacing.spaceExtraSmall)
            .shadow(
                elevation = 1.dp,
                shape = RoundedCornerShape(5.dp)
            )
            .background(MaterialTheme.colors.surface)
            .padding(end = spacing.spaceMedium)
            .height(100.dp)
    ) {
        Image(
            painter = rememberImagePainter(
                data = trackedFood.imageUrl,
                builder = {
                    crossfade(true)
                    error(R.drawable.ic_snack)
                    fallback(R.drawable.ic_snack)
                }
            ),
            contentDescription = trackedFood.name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f)
                .clip(
                    RoundedCornerShape(
                        topStart = 5.dp,
                        bottomStart = 5.dp
                    )
                )
        )
        Spacer(modifier = Modifier.width(spacing.spaceMedium))
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = trackedFood.name,
                style = MaterialTheme.typography.body1,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2
            )
            Spacer(modifier = Modifier.height(spacing.spaceExtraSmall))
            Text(
                text = stringResource(
                    id = R.string.nutrient_info,
                    formatArgs = arrayOf(trackedFood.amount, trackedFood.calories)
                ),
                style = MaterialTheme.typography.body2
            )
        }
        Spacer(modifier = Modifier.width(spacing.spaceMedium))
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxHeight()
        ) {
            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = stringResource(id = R.string.delete),
                modifier = Modifier
                    .align(Alignment.End)
                    .clickable { onDeleteClick() }
            )
            Spacer(modifier = Modifier.height(spacing.spaceExtraSmall))
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                NutrientInfo(
                    amount = trackedFood.carbs,
                    unit = stringResource(id = R.string.grams),
                    nutrientType = stringResource(id = R.string.carbs),
                    amountTextSize = 16.sp,
                    unitTextSize = 12.sp,
                    textStyle = MaterialTheme.typography.body2
                )
                Spacer(modifier = Modifier.width(spacing.spaceSmall))
                NutrientInfo(
                    amount = trackedFood.proteins,
                    unit = stringResource(id = R.string.grams),
                    nutrientType = stringResource(id = R.string.proteins),
                    amountTextSize = 16.sp,
                    unitTextSize = 12.sp,
                    textStyle = MaterialTheme.typography.body2

                )
                Spacer(modifier = Modifier.width(spacing.spaceSmall))
                NutrientInfo(
                    amount = trackedFood.fats,
                    unit = stringResource(id = R.string.grams),
                    nutrientType = stringResource(id = R.string.fats),
                    amountTextSize = 16.sp,
                    unitTextSize = 12.sp,
                    textStyle = MaterialTheme.typography.body2
                )
            }
        }
    }
}