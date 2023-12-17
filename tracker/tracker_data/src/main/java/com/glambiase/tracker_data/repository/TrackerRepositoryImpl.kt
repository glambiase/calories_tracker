package com.glambiase.tracker_data.repository

import com.glambiase.tracker_data.local.TrackerDao
import com.glambiase.tracker_data.mapper.toTrackableFood
import com.glambiase.tracker_data.mapper.toTrackedFood
import com.glambiase.tracker_data.mapper.toTrackedFoodEntity
import com.glambiase.tracker_data.remote.OpenFoodApi
import com.glambiase.tracker_domain.model.TrackableFood
import com.glambiase.tracker_domain.model.TrackedFood
import com.glambiase.tracker_domain.repository.TrackerRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class TrackerRepositoryImpl(
    private val dao: TrackerDao,
    private val api: OpenFoodApi
) : TrackerRepository {

    override suspend fun searchFood(
        query: String,
        page: Int,
        pageSize: Int
    ): Result<List<TrackableFood>> {
        return try {
            val searchDto = api.searchFood(
                query = query,
                page = page,
                pageSize = pageSize
            )
            Result.success(searchDto.products.mapNotNull { product ->
                product.toTrackableFood()
            })
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun insertTrackedFood(trackedFood: TrackedFood) {
        dao.insertTrackedFood(trackedFood.toTrackedFoodEntity())
    }

    override suspend fun deleteTrackedFood(trackedFood: TrackedFood) {
        dao.deleteTrackedFood(trackedFood.toTrackedFoodEntity())
    }

    override fun getFoodByDate(date: LocalDate): Flow<List<TrackedFood>> {
        return dao.getFoodByDate(date.dayOfMonth, date.monthValue, date.year).map { entitiesList ->
           entitiesList.map { trackedFoodEntity ->
               trackedFoodEntity.toTrackedFood()
           }
        }
    }
}