package com.khapp.suji.data

import com.khapp.suji.Constance

data class Resources(val rid:Int, val type:Int, val last_modify_time:String, val path:String) {
    fun getUrl()=Constance.API_RESOURCES_HOST+path
}