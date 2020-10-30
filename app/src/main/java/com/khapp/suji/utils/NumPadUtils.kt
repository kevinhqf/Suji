package com.kevinhqf.app.quicknote.utils

import java.lang.IllegalArgumentException

/**
 * create by heqinfu on 2019-06-09
 **/
object NumPadUtils {


    private fun has2DecimalAfterDot(value: String): Boolean {

        if (value.indexOf(".") == -1)
            return false
        if (value.indexOf(".") == value.length - 3)
            return true
        return false
    }

    fun numpad0Action(value: String): String {
        val withoutDot = value.indexOf('.') == -1
        val canAppend = ((withoutDot && "0" != value) && !has2DecimalAfterDot(value))
                || (!withoutDot && !has2DecimalAfterDot(value))
        return if (canAppend)
            value + "0"
        else
            value
    }

    fun numpadDotAction(value: String): String {
        val canAppend = (value.indexOf('.') == -1)
        return if (canAppend)
            "$value."
        else
            value
    }

    fun numpadBackspaceAction(value: String): String {
        return if (value.length > 1)
            value.substring(0, value.length - 1)
        else "0"
    }

    fun numpadDigitalAction(value: String, digital: Int): String {

        val tmp = when (digital) {
            1 -> "1"
            2 -> "2"
            3 -> "3"
            4 -> "4"
            5 -> "5"
            6 -> "6"
            7 -> "7"
            8 -> "8"
            9 -> "9"
            else -> "-1"
        }
        if (value == "0")
            return tmp
        if (!has2DecimalAfterDot(value))
            return value + tmp
        return value
    }


}