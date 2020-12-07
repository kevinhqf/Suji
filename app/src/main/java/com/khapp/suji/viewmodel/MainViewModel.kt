package com.khapp.suji.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.khapp.suji.Constance
import com.khapp.suji.EMPTY_USER
import com.khapp.suji.preset.Currency
import com.khapp.suji.preset.Language
import com.khapp.suji.preset.Theme
import com.khapp.suji.repository.MainRepository
import com.khapp.suji.repository.UserRepository
import com.khapp.suji.utils.IconSet
import com.khapp.suji.view.comm.BaseViewModel

class MainViewModel(private val mainRepository: MainRepository) : BaseViewModel() {

    val menuPosition = MutableLiveData<Int>(1)

    val appConfig = mainRepository.readConfig().asLiveData()


    fun changeMenuPosition(position: Int) {
        menuPosition.postValue(position)
    }

    fun saveConfig(
        theme: Theme = Constance.config.theme,
        language: Language = Constance.config.language,
        currency: Currency = Constance.config.currency,
        afterSave:()->Unit
    ) {
        launchIO {
            mainRepository.saveConfig(theme, language, currency)
            launchUI { afterSave() }
        }
    }

    fun getLanguage(): Language {
        return appConfig.value?.language ?: Language.CHN
    }
}