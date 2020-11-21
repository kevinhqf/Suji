package com.khapp.suji.utils

import com.khapp.suji.data.TimeUnit
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.util.*

object DateUtils {
    private const val DAY_UNIT = (24 * 60 * 60 * 1000)
    fun getTodayTimeUnit(): TimeUnit {
        val date = Date()
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val today = formatter.format(date)
        val start = formatter.parse(today).time
        val end = start + DAY_UNIT
        return TimeUnit(start, end)
    }

    fun getTodayOfWeek(): Int {
        val instance = Calendar.getInstance()
        return (instance.get(Calendar.DAY_OF_WEEK) - 1)
    }


    fun getDayOfThisWeekTimeUnit(dayOfWeek: Int): TimeUnit {
        val todayOfWeek = getTodayOfWeek()
        val todayTimeUnit = getTodayTimeUnit()
        val i = dayOfWeek - todayOfWeek
        val start = todayTimeUnit.start + (i * DAY_UNIT)
        val end = start + DAY_UNIT
        return TimeUnit(start, end)
    }

    fun getThisWeekTimeUnit(): TimeUnit {
        val delta = 1 - getTodayOfWeek()
        val start = getTodayTimeUnit().start + (delta * DAY_UNIT)
        val end = start + 7 * DAY_UNIT
        return TimeUnit(start, end)
    }

    fun getThisMonthTimeUnit(): TimeUnit {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM", Locale.getDefault())
        val format = simpleDateFormat.format(Date())
        val start = simpleDateFormat.parse(format).time
        val end = start + getActualMaximumOfThisMonth() * DAY_UNIT
        return TimeUnit(start, end)
    }

    fun getActualMaximumOfThisMonth(): Int {
        val instance = Calendar.getInstance()
        return instance.getActualMaximum(Calendar.DAY_OF_MONTH)
    }




}

fun main() {
    val instance = Calendar.getInstance()
    print(instance.getActualMaximum(Calendar.DAY_OF_YEAR))
}
