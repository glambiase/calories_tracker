package com.glambiase.core.domain.model

data class UserInfo(
    val age: Int,
    val sex: Sex,
    val height: Int,
    val weight: Float,
    val activityLevel: ActivityLevel,
    val goal: Goal,
    val carbsRatio: Float,
    val proteinsRatio: Float,
    val fatsRatio: Float
)