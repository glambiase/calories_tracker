package com.glambiase.core.domain.model

sealed class ActivityLevel(val value: String) {

    object Low: ActivityLevel("low")
    object Medium: ActivityLevel("medium")
    object High: ActivityLevel("high")

    companion object {
        fun fromString(value: String): ActivityLevel =
            when (value) {
                "low" -> Low
                "medium" -> Medium
                "high" -> High
                else -> throw IllegalStateException()
            }
    }
}
