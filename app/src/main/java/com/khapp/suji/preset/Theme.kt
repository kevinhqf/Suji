package com.khapp.suji.preset

import java.io.FileDescriptor

enum class Theme(val id:Int, val description: String, val value: Int) {
    LIGHT(1, "浅色", 0),
    DARK(2, "深色", 1),
    AUTO(3, "自动", 2)
}