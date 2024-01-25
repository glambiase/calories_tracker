package com.glambiase.tracker_presentation.tracker_overview

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.glambiase.core.util.UIEvent
import com.glambiase.core_ui.LocalSpacing
import com.glambiase.core.R
import com.glambiase.tracker_presentation.tracker_overview.components.AddButton
import com.glambiase.tracker_presentation.tracker_overview.components.DaySelector
import com.glambiase.tracker_presentation.tracker_overview.components.ExpandableMeal
import com.glambiase.tracker_presentation.tracker_overview.components.NutrientsHeader
import com.glambiase.tracker_presentation.tracker_overview.components.TrackedFoodItem

@Composable
fun TrackerOverviewScreen(
    onNavigate: (UIEvent.Navigate) -> Unit,
    viewModel: TrackerOverviewViewModel = hiltViewModel()
) {
    val spacing = LocalSpacing.current
    val context = LocalContext.current
    val state = viewModel.state

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = spacing.spaceMedium)
    ) {
        item {
            NutrientsHeader(state = state)
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
            DaySelector(
                date = state.date,
                onPreviousDayClick = {
                    viewModel.onEvent(TrackerOverviewEvent.OnPreviousDayClick)
                },
                onNextDayClick = {
                    viewModel.onEvent(TrackerOverviewEvent.OnNextDayClick)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = spacing.spaceMedium)
            )
            Spacer(modifier = Modifier.height(spacing.spaceMedium))
        }
        items(state.meals) { meal ->
            ExpandableMeal(
                meal = meal,
                onToggleClick = { viewModel.onEvent(TrackerOverviewEvent.OnToggleMealClick(meal)) },
                content = {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = spacing.spaceSmall)
                    ) {
                        state.trackedFoodList.forEach { trackedFood ->
                            TrackedFoodItem(
                                trackedFood = trackedFood,
                                onDeleteClick = { viewModel.onEvent(TrackerOverviewEvent.OnDeleteTrackedFoodClick(trackedFood)) }
                            )
                            Spacer(modifier = Modifier.height(spacing.spaceMedium))
                        }
                        AddButton(
                            text = stringResource(id = R.string.add_meal, meal.name.asString(context)),
                            onClick = { viewModel.onEvent(TrackerOverviewEvent.OnAddFoodClick(meal)) },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
