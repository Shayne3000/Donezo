package com.senijoshua.donezo.data.local

import androidx.room.TypeConverter
import kotlinx.datetime.LocalDate

/**
 * Type converter to convert [kotlinx.datetime.LocalDate] to
 * [Long] for persistence in the Room DB.
 */
class DateConverter {
    @TypeConverter
    fun fromLongToLocalDate(value: Long): LocalDate {
        return LocalDate.fromEpochDays(value)
    }

    @TypeConverter
    fun localDateToLong(date: LocalDate): Long {
        return date.toEpochDays()
    }
}
