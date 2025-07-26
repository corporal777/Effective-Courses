package com.examle.effectivecourses.utils

import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtils {

    val dateFormatterFullMothFullYear: DateFormat
        get() = SimpleDateFormat(DATE_FORMAT_FULL_MONTH_FULL_YEAR, Locale.getDefault())

    val defaultServerDateFormatter: DateFormat
        get() = SimpleDateFormat(DATE_FORMAT_SERVER_TIMESTAMP, Locale.getDefault())

    fun String.formatToDefaultDayMonthYearDate(): String? {
        val date = parseAndFormat(defaultServerDateFormatter, dateFormatterFullMothFullYear)
        return StringBuilder().apply {
            var isFormatted = false
            date?.forEach {
                if (!isFormatted && it.isLetter()){
                    append(it.uppercase())
                    isFormatted = true
                }else append(it)
            }
        }.toString()
    }

    private fun String.parseAndFormat(parser: DateFormat, formatter: DateFormat): String? {
        return parseToDate(parser)?.let { formatter.format(it) }
    }

    fun String.parseToDate(parser: DateFormat): Date? {
        return try {
            parser.parse(this)
        } catch (e: ParseException) {
            null
        }
    }

    fun String.timeMillis(): Long {
        return parseToDate(defaultServerDateFormatter)?.time ?: 0
    }
}