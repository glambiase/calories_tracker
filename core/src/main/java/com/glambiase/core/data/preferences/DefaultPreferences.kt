package com.glambiase.core.data.preferences

import android.content.SharedPreferences
import com.glambiase.core.domain.model.ActivityLevel
import com.glambiase.core.domain.model.Goal
import com.glambiase.core.domain.model.Sex
import com.glambiase.core.domain.model.UserInfo
import com.glambiase.core.domain.preferences.Preferences

class DefaultPreferences(
    private val sharedPreferences: SharedPreferences
): Preferences {

    override fun saveAge(age: Int) =
        sharedPreferences
            .edit()
            .putInt(Preferences.KEY_AGE, age)
            .apply()

    override fun saveSex(sex: Sex) =
        sharedPreferences
            .edit()
            .putString(Preferences.KEY_SEX, sex.value)
            .apply()

    override fun saveHeight(height: Int) =
        sharedPreferences
            .edit()
            .putInt(Preferences.KEY_HEIGHT, height)
            .apply()

    override fun saveWeight(weight: Float) =
        sharedPreferences
            .edit()
            .putFloat(Preferences.KEY_WEIGHT, weight)
            .apply()

    override fun saveActivityLevel(activityLevel: ActivityLevel) =
        sharedPreferences
            .edit()
            .putString(Preferences.KEY_ACTIVITY_LEVEL, activityLevel.value)
            .apply()

    override fun saveGoal(goal: Goal) =
        sharedPreferences
            .edit()
            .putString(Preferences.KEY_GOAL, goal.value)
            .apply()

    override fun saveCarbsRatio(ratio: Float) =
        sharedPreferences
            .edit()
            .putFloat(Preferences.KEY_CARB_RATIO, ratio)
            .apply()

    override fun saveProteinsRatio(ratio: Float) =
        sharedPreferences
            .edit()
            .putFloat(Preferences.KEY_PROTEIN_RATIO, ratio)
            .apply()

    override fun saveFatsRatio(ratio: Float) =
        sharedPreferences
            .edit()
            .putFloat(Preferences.KEY_FAT_RATIO, ratio)
            .apply()

    override fun loadUserInfo(): UserInfo {
        val age = sharedPreferences.getInt(Preferences.KEY_AGE, -1)
        val sex = sharedPreferences.getString(Preferences.KEY_SEX, null)
        val height = sharedPreferences.getInt(Preferences.KEY_HEIGHT, -1)
        val weight = sharedPreferences.getFloat(Preferences.KEY_WEIGHT, -1f)
        val activityLevel = sharedPreferences.getString(Preferences.KEY_ACTIVITY_LEVEL, null)
        val goal = sharedPreferences.getString(Preferences.KEY_GOAL, null)
        val carbRatio = sharedPreferences.getFloat(Preferences.KEY_CARB_RATIO, -1f)
        val proteinRatio = sharedPreferences.getFloat(Preferences.KEY_PROTEIN_RATIO, -1f)
        val fatRatio = sharedPreferences.getFloat(Preferences.KEY_FAT_RATIO, -1f)

        return UserInfo(
            age = age,
            sex = Sex.fromString(sex.orEmpty()),
            height = height,
            weight = weight,
            activityLevel = ActivityLevel.fromString(activityLevel.orEmpty()),
            goal = Goal.fromString(goal.orEmpty()),
            carbsRatio = carbRatio,
            proteinsRatio = proteinRatio,
            fatsRatio = fatRatio
        )
    }

    override fun saveShouldShowOnboarding(shouldShowOnboarding: Boolean) =
        sharedPreferences
            .edit()
            .putBoolean(Preferences.KEY_SHOW_ONBOARDING, shouldShowOnboarding)
            .apply()

    override fun loadShouldShowOnboarding() =
        sharedPreferences.getBoolean(Preferences.KEY_SHOW_ONBOARDING, true)
}