package com.glambiase.tracker_domain.di

import com.glambiase.core.domain.preferences.Preferences
import com.glambiase.tracker_domain.repository.TrackerRepository
import com.glambiase.tracker_domain.use_case.CalculateMealNutrientsUseCase
import com.glambiase.tracker_domain.use_case.DeleteFoodUseCase
import com.glambiase.tracker_domain.use_case.GetFoodByDateUseCase
import com.glambiase.tracker_domain.use_case.SearchFoodUseCase
import com.glambiase.tracker_domain.use_case.TrackFoodUseCase
import com.glambiase.tracker_domain.use_case.TrackerUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object TrackerDomainModule {

    @Provides
    @ViewModelScoped
    fun provideTrackerUseCases(
        preferences: Preferences,
        repository: TrackerRepository
    ): TrackerUseCases =
        TrackerUseCases(
            calculateMealNutrientsUseCase = CalculateMealNutrientsUseCase(preferences),
            deleteFoodUseCase = DeleteFoodUseCase(repository),
            getFoodByDateUseCase = GetFoodByDateUseCase(repository),
            searchFoodUseCase = SearchFoodUseCase(repository),
            trackFoodUseCase = TrackFoodUseCase(repository)
        )
}