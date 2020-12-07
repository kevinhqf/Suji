package com.khapp.suji.utils

import android.content.Context
import com.khapp.suji.App
import com.khapp.suji.R
import com.khapp.suji.data.AnalysisTimeUnit
import com.khapp.suji.data.GraphTimeUnit
import com.khapp.suji.data.TimeUnit
import java.text.SimpleDateFormat
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

    fun getGraphTimeUnitArr(context:Context,timeUnit: AnalysisTimeUnit): ArrayList<GraphTimeUnit> {
        val graphTimeUnit = ArrayList<GraphTimeUnit>()
        return when (timeUnit) {
            AnalysisTimeUnit.THIS_WEEK -> {
                val times = arrayOf(
                    context.getString(R.string.monday_text),
                    context.getString(R.string.tuesday_text),
                    context.getString(R.string.wednesday_text),
                    context.getString(R.string.thursday_text),
                    context.getString(R.string.friday_text),
                    context.getString(R.string.saturday_text),
                    context.getString(R.string.sunday_text)
                )
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
                    getMonthString(context,thisMonthOfYear - 5),
                    getMonthString(context,thisMonthOfYear - 4),
                    getMonthString(context,thisMonthOfYear - 3),
                    getMonthString(context,thisMonthOfYear - 2),
                    getMonthString(context,thisMonthOfYear - 1),
                    getMonthString(context,thisMonthOfYear)
                )
                for (i in 5 downTo 0) {
                    val month = thisMonthOfYear - i
                    graphTimeUnit.add(
                        GraphTimeUnit(
                            getMonthString(context,month),
                            getMonthOfThisYearTimeUnit(month)
                        )
                    )
                }
                graphTimeUnit
            }
        }
    }

    private fun getMonthString(context:Context, month: Int): String {
        return when (month) {
            1 -> {
                context.getString(R.string.january_text)
            }
            2 -> {
                context.getString(R.string.february_text)
            }
            3 -> {
                context.getString(R.string.march_text)
            }
            4 -> {
                context.getString(R.string.april_text)
            }
            5 -> {
                context.getString(R.string.may_text)
            }
            6 -> {
                context.getString(R.string.june_text)
            }
            7 -> {
                context.getString(R.string.july_text)
            }
            8 -> {
                context.getString(R.string.august_text)
            }
            9 -> {
                context.getString(R.string.september_text)
            }
            10 -> {
                context.getString(R.string.october_text)
            }
            11 -> {
                context.getString(R.string.november_text)
            }
            12 -> {
                context.getString(R.string.december_text)
            }
            else -> ""
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
