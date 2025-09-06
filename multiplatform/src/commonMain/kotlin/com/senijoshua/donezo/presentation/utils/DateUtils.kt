package com.senijoshua.donezo.presentation.utils

import kotlinx.datetime.LocalDate
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char

/**
 * Utility for LocalDate operations like formatting.
 */
fun getDateFormat(): DateTimeFormat<LocalDate> {
    return LocalDate.Format {
        day()
        char(' ')
        monthName(names = MonthNames.ENGLISH_ABBREVIATED)
        char(' ')
        year()
    }
}
