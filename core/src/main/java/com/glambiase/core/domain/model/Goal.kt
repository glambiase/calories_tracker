package com.glambiase.core.domain.model

sealed class Goal(val value: String) {

    object LoseWeight: Goal("lose_weight")
    object KeepWeight: Goal("keep_weight")
    object GainWeight: Goal("gain_weight")

    companion object {
        fun fromString(value: String): Goal =
            when (value) {
                "lose_weight" -> LoseWeight
                "keep_weight" -> KeepWeight
                "gain_weight" -> GainWeight
                else -> throw IllegalStateException()
            }
    }
}