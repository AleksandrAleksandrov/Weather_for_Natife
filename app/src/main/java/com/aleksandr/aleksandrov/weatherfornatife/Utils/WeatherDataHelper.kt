package com.aleksandr.aleksandrov.weatherfornatife.Utils

import com.aleksandr.aleksandrov.weatherfornatife.api.models.Day
import com.aleksandr.aleksandrov.weatherfornatife.api.models.DayInfo
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by Alexandrov Alex on 2019-06-02.
 */
class WeatherDataHelper {

    companion object {

        fun sortDays(info: List<DayInfo>) : MutableList<Day> {
            val days: MutableList<Day> = ArrayList()

            info.iterator().forEach {
                placeInDays(days, it)
            }
            return days
        }

        private fun placeInDays(days: MutableList<Day>, info: DayInfo) {
            if (days.size == 0) {
                val day = Day()
                day.dayInfo.add(info)
                days.add(day)
            } else {
                days.iterator().forEach {
                    if (it.dayInfo.size == 0) {
                        it.dayInfo.add(info)
                        return
                    } else if (isSameDay(it.dayInfo[0].dt* 1000, info.dt* 1000)) {
                        it.dayInfo.add(info)
                        return
                    }
                }
                val day = Day()
                day.dayInfo.add(info)
                days.add(day)
            }

        }

        private fun isSameDay(time1 : Long, time2 : Long) : Boolean {
            val cal1 = Calendar.getInstance()
            val cal2 = Calendar.getInstance()
            cal1.timeInMillis = time1
            cal2.timeInMillis = time2
            cal1.timeZone = TimeZone.getTimeZone("UTC")
            cal2.timeZone = TimeZone.getTimeZone("UTC")
            return cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR) && cal1.get(Calendar.YEAR) == cal2.get(
                Calendar.YEAR)
        }
    }

}
