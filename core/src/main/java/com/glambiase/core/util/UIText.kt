package com.glambiase.core.util

import android.content.Context

sealed class UIText {
    data class DynamicString(val text: String): UIText()
    data class StringResource(val resId: Int): UIText()

    fun asString(context: Context) =
        when (this) {
            is DynamicString -> text
            is StringResource -> context.getString(resId)
        }
}