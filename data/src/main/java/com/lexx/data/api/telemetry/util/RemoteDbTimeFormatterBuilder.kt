package com.lexx.data.api.telemetry.util

import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.format.SignStyle
import java.time.temporal.ChronoField

class RemoteDbTimeFormatterBuilder {

    fun build() : DateTimeFormatter {
        val dow: MutableMap<Long, String> = HashMap()
        dow[1L] = "Mon"
        dow[2L] = "Tue"
        dow[3L] = "Wed"
        dow[4L] = "Thu"
        dow[5L] = "Fri"
        dow[6L] = "Sat"
        dow[7L] = "Sun"
        val moy: MutableMap<Long, String> = HashMap()
        moy[1L] = "Jan"
        moy[2L] = "Feb"
        moy[3L] = "Mar"
        moy[4L] = "Apr"
        moy[5L] = "May"
        moy[6L] = "Jun"
        moy[7L] = "Jul"
        moy[8L] = "Aug"
        moy[9L] = "Sep"
        moy[10L] = "Oct"
        moy[11L] = "Nov"
        moy[12L] = "Dec"
        return DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .parseLenient()
            .appendText(ChronoField.DAY_OF_WEEK, dow)
            .appendLiteral(' ')
            .appendText(ChronoField.MONTH_OF_YEAR, moy)
            .appendLiteral(' ')
            .optionalStart()
            .appendLiteral(' ')
            .optionalEnd()
            .appendValue(ChronoField.DAY_OF_MONTH, 1, 2, SignStyle.NOT_NEGATIVE)
            .appendLiteral(' ')
            .appendValue(ChronoField.HOUR_OF_DAY, 2)
            .appendLiteral(':')
            .appendValue(ChronoField.MINUTE_OF_HOUR, 2)
            .appendLiteral(':')
            .appendValue(ChronoField.SECOND_OF_MINUTE, 2)
            .appendLiteral(' ')
            .appendOffset("+HHMM", "UTC")
            .appendLiteral(' ')
            .appendValue(ChronoField.YEAR, 4)
            .toFormatter()
    }
}
