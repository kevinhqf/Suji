package com.khapp.suji.preset

import androidx.paging.Config

data class AppConfig(
    var theme: Theme = Theme.AUTO,
    var language: Language = Language.CHN,
    var currency: Currency = Currency.CNY
) {
}