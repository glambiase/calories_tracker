package com.glambiase.core.domain.model

sealed class Sex(val value: String) {
    companion object {
        fun fromString(value: String): Sex =
            when (value) {
                "female" -> Female
                "male" -> Male
                else -> throw IllegalStateException() // TODO: handle exception FP way
            }
    }

    object Female: Sex("female")
    object Male: Sex("male")
}