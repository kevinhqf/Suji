package com.khapp.suji.utils

import android.content.Context
import android.widget.Toast
import com.khapp.suji.data.TimeUnit
import java.text.SimpleDateFormat
import java.util.*

object Utils {
    fun formatMoney(money: Double): String {
        var sign = "￥"
        var tmp = money
        if (money < 0) {
            sign = "-￥"
            tmp = money * -1
        }
        return when {
            tmp >= 1000000000 -> {
                val num = (tmp / 1000000000).toInt()
                sign + num + "B"
            }
            tmp >= 1000000 -> {
                val num = (tmp / 1000000).toInt()
                sign + num + "M"
            }
            tmp >= 1000 -> {
                val num = (tmp / 1000).toInt()
                sign + num + "K"
            }
            else -> sign + String.format("%,.2f", tmp)
        }
    }

    /**
     * 格式化numpad的金钱数值
     */
    fun formatNumpadMoney(value: String): String {
        val group = value.split(".")
        val int = String.format("%,.0f", group[0].toDouble())
        return if (group.size > 1) "$int.${group[1]}" else int
    }

    fun formatMoneyValue(value: Float): String {
        val group = value.toString().split(".")
        val int = String.format("%,.0f", group[0].toDouble())
        return if (group.size > 1 && group[1] != "0") "$int.${group[1]}" else int
    }


    fun toast(context: Context, text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    fun getCurrentTimeStr(pattern: String = "yyyy-MM-dd HH:mm:ss"): String {
        return SimpleDateFormat(pattern, Locale.getDefault()).format(Date())
    }

    fun formatTransactionTime(time: Long): String {
        val date = Date(time)
        val sign = if (date.hours >= 12) "PM" else "AM"
        return SimpleDateFormat("MM-dd,yyyy h:mm", Locale.getDefault()).format(date) + sign
    }

    fun getTodayTimeUnit(): TimeUnit {
        val date = Date()
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val today = formatter.format(date)
        val start = formatter.parse(today).time
        val end = start + (24 * 60 * 60 * 1000)
        return TimeUnit(start, end)
    }

    fun getFormatMoneyStr(value: Float): String {
        val sign = if (value >= 0) "" else "-"

        return "$sign￥${String.format("%,.2f", value).replace("-","")}"
    }
}


