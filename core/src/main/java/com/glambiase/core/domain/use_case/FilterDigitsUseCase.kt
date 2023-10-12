package com.glambiase.core.domain.use_case

class FilterDigitsUseCase {

    operator fun invoke(text: String) = text.filter { it.isDigit() }
}