package com.khapp.suji.utils

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

    fun formatNumpadMoney(value:String):String{
        val group = value.split(".")
        val int = String.format("%,.0f",group[0].toDouble())
        return if (group.size>1) "$int.${group[1]}" else int
    }
}

