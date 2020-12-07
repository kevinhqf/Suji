package com.khapp.suji.utils

import android.content.Context
import android.widget.Toast
import com.khapp.suji.Constance
import com.khapp.suji.data.TimeUnit
import com.khapp.suji.preset.Currency
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.*

object Utils {
    fun formatChartScale(value: Float): String {
        return when {
            value >= 10000 -> {
                val num = (value / 10000)
                "${String.format("%.1f", num)}W"
            }
            value >= 1000 -> {
                val num = (value / 1000)
                "${String.format("%.1f", num)}K"
            }
            else -> value.toInt().toString()
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

        return "$sign${Constance.config.currency.sign}${String.format("%,.2f", value).replace("-", "")}"
    }

    /**
     * md5加密字符串
     * md5使用后转成16进制变成32个字节
     */
    fun md5(str: String): String {
        val digest = MessageDigest.getInstance("MD5")
        val result = digest.digest(str.toByteArray())
        //没转16进制之前是16位
        //println("result${result.size}")
        //转成16进制后是32字节
        return toHex(result)
    }

    fun toHex(byteArray: ByteArray): String {
        val result = with(StringBuilder()) {
            byteArray.forEach {
                val hex = it.toInt() and (0xFF)
                val hexStr = Integer.toHexString(hex)
                if (hexStr.length == 1) {
                    this.append("0").append(hexStr)
                } else {
                    this.append(hexStr)
                }
            }
            this.toString()
        }
        //转成16进制后是32字节
        return result
    }
}



