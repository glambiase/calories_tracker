package com.glambiase.tracker_presentation.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glambiase.core.domain.use_case.FilterDigitsUseCase
import com.glambiase.core.util.UIEvent
import com.glambiase.core.util.UIText
import com.glambiase.tracker_domain.use_case.TrackerUseCases
import com.glambiase.core.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val trackerUseCases: TrackerUseCases,
    private val filterDigitsUseCase: FilterDigitsUseCase
) : ViewModel() {

    var state by mutableStateOf(SearchState())
        private set

    private val _uiEvent = Channel<UIEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.OnQueryChange -> {
                state = state.copy(query = event.query)
            }
            is SearchEvent.OnFoodAmountChange -> {
                state = state.copy(
                    trackableFoodList = state.trackableFoodList.map {
                        if (it.trackableFood == event.food) {
                            it.copy(amount = filterDigitsUseCase(event.amount))
                        } else it
                    }
                )
            }
            is SearchEvent.OnToggleTrackableFood -> {
                state = state.copy(
                    trackableFoodList = state.trackableFoodList.map {
                        if (it.trackableFood == event.food) {
                            it.copy(isExpanded = !it.isExpanded)
                        } else it
                    }
                )
            }
            is SearchEvent.OnTrackFoodClick -> {
                trackFood(event)
            }
            is SearchEvent.OnSearchFocusChange -> {
                state = state.copy(
                    isHintVisible = !event.isFocused && state.query.isBlank()
                )
            }
            SearchEvent.OnSearch -> {
                executeSearch()
            }
        }
    }

    private fun trackFood(event: SearchEvent.OnTrackFoodClick) {
        viewModelScope.launch {
            val uiState = state.trackableFoodList.find {
                it.trackableFood == event.food
            }
            trackerUseCases.trackFoodUseCase(
                trackableFood = uiState?.trackableFood ?: return@launch,
                amount = uiState.amount.toIntOrNull() ?: return@launch,
                mealType = event.mealType,
                date = event.date
            )
            _uiEvent.send(UIEvent.NavigateUp)
        }
    }

    private fun executeSearch() {
        viewModelScope.launch {
            state = state.copy(
                isSearching = true,
                trackableFoodList = emptyList()
            )
            trackerUseCases.searchFoodUseCase(state.query)
                .onSuccess {
                    state = state.copy(
                        isSearching = false,
                        trackableFoodList = it.map { trackableFood ->
                            TrackableFoodUiState(trackableFood = trackableFood)
                        }
                    )
                }
                .onFailure {
                    state = state.copy(isSearching = false)
                    _uiEvent.send(
                        UIEvent.ShowSnackbar(
                            UIText.StringResource(R.string.error_something_went_wrong)
                        )
                    )
                }
        }
    }
}