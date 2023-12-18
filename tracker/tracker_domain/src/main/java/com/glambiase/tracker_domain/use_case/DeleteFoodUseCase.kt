package com.glambiase.tracker_domain.use_case

import com.glambiase.tracker_domain.model.TrackedFood
import com.glambiase.tracker_domain.repository.TrackerRepository

class DeleteFoodUseCase(
    private val repository: TrackerRepository
) {
    suspend operator fun invoke(
        trackedFood: TrackedFood
    ) {
        repository.deleteTrackedFood(trackedFood)
    }
}