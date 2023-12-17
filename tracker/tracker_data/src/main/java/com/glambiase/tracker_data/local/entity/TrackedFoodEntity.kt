package com.glambiase.tracker_data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TrackedFoodEntity(
    val name: String,
    val imageUrl: String?,
    val calories: Int,
    val carbs: Int,
    val fats: Int,
    val proteins: Int,
    val type: String,
    val amount: Int,
    val dayOfMonth: Int,
    val month: Int,
    val year: Int,
    @PrimaryKey val id: Int? = null
)