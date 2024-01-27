package com.glambiase.tracker_presentation.search

import com.glambiase.tracker_domain.model.TrackableFood

data class TrackableFoodUiState(
    val trackableFood: TrackableFood,
    val isExpanded: Boolean = false,
    val amount: String = ""
)