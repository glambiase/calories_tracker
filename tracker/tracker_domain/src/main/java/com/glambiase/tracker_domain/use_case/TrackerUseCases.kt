package com.glambiase.tracker_domain.use_case

data class TrackerUseCases(
    val calculateMealNutrientsUseCase: CalculateMealNutrientsUseCase,
    val deleteFoodUseCase: DeleteFoodUseCase,
    val getFoodByDateUseCase: GetFoodByDateUseCase,
    val searchFoodUseCase: SearchFoodUseCase,
    val trackFoodUseCase: TrackFoodUseCase
)