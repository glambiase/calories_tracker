package com.glambiase.core.domain.preferences

import com.glambiase.core.domain.model.ActivityLevel
import com.glambiase.core.domain.model.Goal
import com.glambiase.core.domain.model.Sex
import com.glambiase.core.domain.model.UserInfo

interface Preferences {
    companion object {
        const val KEY_AGE = "age"
        const val KEY_SEX = "sex"
        const val KEY_HEIGHT = "height"
        const val KEY_WEIGHT = "weight"
        const val KEY_ACTIVITY_LEVEL = "activity_level"
        const val KEY_GOAL = "goal"
        const val KEY_CARB_RATIO = "carb_ratio"
        const val KEY_PROTEIN_RATIO = "protein_ratio"
        const val KEY_FAT_RATIO = "fat_ratio"
    }

    fun saveAge(age: Int)
    fun saveSex(sex: Sex)
    fun saveHeight(height: Int)
    fun saveWeight(weight: Float)
    fun saveActivityLevel(activityLevel: ActivityLevel)
    fun saveGoal(goal: Goal)
    fun saveCarbRatio(ratio: Float)
    fun saveProteinRatio(ratio: Float)
    fun saveFatRatio(ratio: Float)

    fun loadUserInfo(): UserInfo
}