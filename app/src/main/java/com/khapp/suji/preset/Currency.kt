package com.khapp.suji.preset

import com.khapp.suji.R

enum class Currency(val id: Int, val sign: String, val description: String, val icon: Int) {
    AUD(1, "$", "AUD", R.mipmap.icon_currency_aud),
    MOP(2, "$", "MOP", R.mipmap.icon_currency_mop),
    RUB(3, "₽", "RUB", R.mipmap.icon_currency_rub),
    HKD(4, "$", "HKD", R.mipmap.icon_currency_hkd),
    KRW(5, "₩", "KRW", R.mipmap.icon_currency_krw),
    CAD(6, "$", "CAD", R.mipmap.icon_currency_cad),
    MXN(7, "$", "MXN", R.mipmap.icon_currency_mxn),
    USD(8, "$", "USD", R.mipmap.icon_currency_usd),
    EUR(9, "€", "EUR", R.mipmap.icon_currency_eur),
    CNY(10, "￥", "CNY", R.mipmap.icon_currency_cny),
    JPY(11, "¥", "JPY", R.mipmap.icon_currency_jpy),
    THB(12, "฿", "THB", R.mipmap.icon_currency_thb),
    NZD(13, "$", "NZD", R.mipmap.icon_currency_nzd),
    SGD(14, "$", "SGD", R.mipmap.icon_currency_sgd),
    GBP(15, "£", "GBP", R.mipmap.icon_currency_aud),
    INR(16, "₹", "INR", R.mipmap.icon_currency_inr),
    VND(17, "₫", "VND", R.mipmap.icon_currency_vnd)

}