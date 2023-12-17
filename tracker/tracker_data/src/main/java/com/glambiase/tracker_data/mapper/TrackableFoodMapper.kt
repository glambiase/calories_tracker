package com.glambiase.tracker_data.mapper

import com.glambiase.tracker_data.remote.dto.Product
import com.glambiase.tracker_domain.model.TrackableFood
import kotlin.math.roundToInt

fun Product.toTrackableFood(): TrackableFood? {
    return TrackableFood(
        name = productName ?: return null,
        imageUrl = imageFrontThumbUrl,
        caloriesPer100g = nutriments.energyKcal100g.roundToInt(),
        carbsPer100g = nutriments.carbohydrates100g.roundToInt(),
        fatsPer100g = nutriments.fats100g.roundToInt(),
        proteinsPer100g = nutriments.proteins100g.roundToInt()
    )
}