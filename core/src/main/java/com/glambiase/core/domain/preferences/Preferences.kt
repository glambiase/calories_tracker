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
        const val KEY_SHOW_ONBOARDING = "show_onboarding"
    }

    fun saveAge(age: Int)
    fun saveSex(sex: Sex)
    fun saveHeight(height: Int)
    fun saveWeight(weight: Float)
    fun saveActivityLevel(activityLevel: ActivityLevel)
    fun saveGoal(goal: Goal)
    fun saveCarbsRatio(ratio: Float)
    fun saveProteinsRatio(ratio: Float)
    fun saveFatsRatio(ratio: Float)

    fun loadUserInfo(): UserInfo

    fun saveShouldShowOnboarding(shouldShowOnboarding: Boolean)
    fun loadShouldShowOnboarding(): Boolean
}