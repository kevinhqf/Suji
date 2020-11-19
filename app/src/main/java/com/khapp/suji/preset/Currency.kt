package com.khapp.suji.preset

import com.khapp.suji.R

enum class Currency(val sign:String, val description:String, val icon:Int) {
    AUD("$","AUD", R.mipmap.icon_currency_aud),
    MOP("$","MOP", R.mipmap.icon_currency_mop),
    RUB("₽","RUB", R.mipmap.icon_currency_rub),
    HKD("$","HKD", R.mipmap.icon_currency_hkd),
    KRW("₩","KRW", R.mipmap.icon_currency_krw),
    CAD("$","CAD", R.mipmap.icon_currency_cad),
    MXN("$","MXN", R.mipmap.icon_currency_mxn),
    USD("$","USD", R.mipmap.icon_currency_usd),
    EUR("€","EUR", R.mipmap.icon_currency_eur),
    CNY("￥","CNY", R.mipmap.icon_currency_cny),
    JPY("¥","JPY", R.mipmap.icon_currency_jpy),
    THB("฿","THB", R.mipmap.icon_currency_thb),
    NZD("$","NZD", R.mipmap.icon_currency_nzd),
    SGD("$","SGD", R.mipmap.icon_currency_sgd),
    GBP("£","GBP", R.mipmap.icon_currency_aud),
    INR("₹","INR", R.mipmap.icon_currency_inr),
    VND("₫","VND", R.mipmap.icon_currency_vnd)
}