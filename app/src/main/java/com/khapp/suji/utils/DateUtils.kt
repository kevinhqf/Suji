package com.khapp.suji.utils

import com.khapp.suji.data.AnalysisTimeUnit
import com.khapp.suji.data.GraphTimeUnit
import com.khapp.suji.data.TimeUnit
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.util.*
import kotlin.collections.ArrayList

object DateUtils {
    private const val DAY_UNIT: Long = (24 * 60 * 60 * 1000)
    fun getTodayTimeUnit(): TimeUnit {
        val date = Date()
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val today = formatter.format(date)
        val start = formatter.parse(today)?.time!!
        val end = start + DAY_UNIT
        return TimeUnit(start, end)
    }

    private fun getTodayOfWeek(): Int {
        val instance = Calendar.getInstance()
        val index = instance.get(Calendar.DAY_OF_WEEK) - 1
        return if (index == 0) 7 else index
    }

    private fun getThisMonthOfYear(): Int {
        val instance = Calendar.getInstance()
        return instance.get(Calendar.MONTH) + 1
    }


    private fun getDayOfThisWeekTimeUnit(dayOfWeek: Int): TimeUnit {
        val todayOfWeek = getTodayOfWeek()
        val todayTimeUnit = getTodayTimeUnit()
        val i = dayOfWeek - todayOfWeek
        val start = todayTimeUnit.start + (i * DAY_UNIT)
        val end = start + DAY_UNIT
        return TimeUnit(start, end)
    }

    private fun getThisMonthTimeUnitOf(index: Int): TimeUnit {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM", Locale.getDefault())
        val format = simpleDateFormat.format(Date())
        val realIndex = if (index >= 3) 3 else index
        val start = simpleDateFormat.parse(format).time + (7 * realIndex * DAY_UNIT)
        val end = if (realIndex == 3) {
            start + (getActualMaximumOfThisMonth() - (realIndex * 7)) * DAY_UNIT
        } else {
            start + 7 * DAY_UNIT
        }
        return TimeUnit(start, end)
    }

    private fun getMonthOfThisYearTimeUnit(monthOfYear: Int): TimeUnit {
        val formatter = SimpleDateFormat("yyyy-MM", Locale.getDefault())
        val instance = Calendar.getInstance()
        instance.set(Calendar.MONTH, monthOfYear - 1)
        val format = formatter.format(instance.time)
        val start = formatter.parse(format)?.time!!
        val end = start + getActualMaximumOf(monthOfYear) * DAY_UNIT
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
        val start = simpleDateFormat.parse(format)?.time!!
        val end = start + (getActualMaximumOfThisMonth() * DAY_UNIT)
        return TimeUnit(start, end)
    }

    private fun getActualMaximumOfThisMonth(): Int {
        val instance = Calendar.getInstance()
        return instance.getActualMaximum(Calendar.DAY_OF_MONTH)
    }

    private fun getActualMaximumOf(month: Int): Int {
        val instance = Calendar.getInstance()
        instance.set(Calendar.MONTH, month - 1)
        return instance.getActualMaximum(Calendar.DAY_OF_MONTH)
    }

    fun getLatelyHalfYearTimeUnit(): TimeUnit {
        val formatter = SimpleDateFormat("yyyy-MM", Locale.getDefault())
        val instance = Calendar.getInstance()
        instance.add(Calendar.MONTH, -5)
        val startMonth = formatter.format(instance.time)
        val start = formatter.parse(startMonth)?.time!!
        val end = getThisMonthTimeUnit().end
        return TimeUnit(start, end)
    }

    fun getGraphTimeUnitArr(timeUnit: AnalysisTimeUnit): ArrayList<GraphTimeUnit> {
        val graphTimeUnit = ArrayList<GraphTimeUnit>()
        return when (timeUnit) {
            AnalysisTimeUnit.THIS_WEEK -> {
                val times = arrayOf("周一", "周二", "周三", "周四", "周五", "周六", "周日")
                times.forEachIndexed { index, title ->
                    graphTimeUnit.add(GraphTimeUnit(title, getDayOfThisWeekTimeUnit(index + 1)))
                }
                graphTimeUnit
            }

            AnalysisTimeUnit.THIS_MONTH -> {
                val times = arrayOf("1~7", "8~14", "15~21", "22~${getActualMaximumOfThisMonth()}")
                times.forEachIndexed { index, title ->
                    graphTimeUnit.add(GraphTimeUnit(title, getThisMonthTimeUnitOf(index)))
                }
                graphTimeUnit
            }

            AnalysisTimeUnit.LATELY_HALF_YEAR -> {
                val thisMonthOfYear = getThisMonthOfYear()
                arrayOf(
                    "${thisMonthOfYear - 5}月",
                    "${thisMonthOfYear - 4}月",
                    "${thisMonthOfYear - 3}月",
                    "${thisMonthOfYear - 2}月",
                    "${thisMonthOfYear - 1}月",
                    "${thisMonthOfYear}月"
                )
                for (i in 5 downTo 0){
                    var month = thisMonthOfYear-i
                    graphTimeUnit.add(GraphTimeUnit("${month}月",
                        getMonthOfThisYearTimeUnit(month)))
                }
                graphTimeUnit
            }
        }
    }


}

//fun main() {
//    val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
//    DateUtils.getGraphTimeUnitArr(AnalysisTimeUnit.THIS_WEEK).forEach {
//        println("${it.title},start = ${formatter.format(it.time.start)},end=${formatter.format(it.time.end)}")
//    }
//    println()
//    DateUtils.getGraphTimeUnitArr(AnalysisTimeUnit.THIS_MONTH).forEach {
//        println("${it.title},start = ${formatter.format(it.time.start)},end=${formatter.format(it.time.end)}")
//    }
//    println()
//    DateUtils.getGraphTimeUnitArr(AnalysisTimeUnit.LATELY_HALF_YEAR).forEach {
//        println("${it.title},start = ${formatter.format(it.time.start)},end=${formatter.format(it.time.end)}")
//    }
//
//}
