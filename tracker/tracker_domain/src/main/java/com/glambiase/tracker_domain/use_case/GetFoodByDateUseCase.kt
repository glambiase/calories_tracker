package com.glambiase.tracker_domain.use_case

import com.glambiase.tracker_domain.model.TrackedFood
import com.glambiase.tracker_domain.repository.TrackerRepository
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class GetFoodByDateUseCase(
    private val repository: TrackerRepository
) {
    operator fun invoke(
        date: LocalDate
    ): Flow<List<TrackedFood>> {
        return repository.getFoodByDate(date)
    }
}