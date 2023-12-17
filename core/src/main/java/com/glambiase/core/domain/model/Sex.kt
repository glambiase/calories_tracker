package com.glambiase.core.domain.model

sealed class Sex(val value: String) {

    object Female: Sex("female")
    object Male: Sex("male")

    companion object {
        fun fromString(value: String): Sex =
            when (value) {
                "female" -> Female
                "male" -> Male
                else -> throw IllegalStateException()
            }
    }
}