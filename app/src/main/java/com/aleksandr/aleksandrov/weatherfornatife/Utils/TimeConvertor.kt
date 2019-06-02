package com.aleksandr.aleksandrov.weatherfornatife.Utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Alexandrov Alex on 2019-06-01.
 */
class TimeConvertor {

    companion object {

        private const val fullDayPattern = "EE, dd MMM"
        private const val dayOfWeekPattern = "EE"
        private const val hoursPattern = "HH"

        fun convertToFullDate(time: Long) : String {
            val cal = Calendar.getInstance()
            cal.timeInMillis = time * 1000
            cal.timeZone = TimeZone.getTimeZone("UTC")
            val fullDate = SimpleDateFormat(fullDayPattern, Locale.getDefault())
            fullDate.timeZone = TimeZone.getTimeZone("UTC")
            val finalDay = fullDate.format(cal.time)
            return finalDay
        }

        fun convertToDayOfWeek(time: Long) : String {
            val cal = Calendar.getInstance()
            cal.timeInMillis = time * 1000
            cal.timeZone = TimeZone.getTimeZone("UTC")
            val dayOfWeek = SimpleDateFormat(dayOfWeekPattern, Locale.getDefault())
            dayOfWeek.timeZone = TimeZone.getTimeZone("UTC")
            val finalDay = dayOfWeek.format(cal.time)
            return finalDay
        }

        fun convertToHours(time: Long) : String {
            val cal = Calendar.getInstance()
            cal.timeZone = TimeZone.getTimeZone("UTC")
            cal.timeInMillis = time * 1000
            val hours = SimpleDateFormat(hoursPattern, Locale.getDefault())
            hours.timeZone = TimeZone.getTimeZone("UTC")
            return hours.format(cal.time)
        }
    }
}