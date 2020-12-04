package com.khapp.suji.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.khapp.suji.CODE_SUCCESS
import com.khapp.suji.CODE_USER_LOGIN
import com.khapp.suji.CODE_USER_SIGNUP
import com.khapp.suji.EMPTY_USER
import com.khapp.suji.api.API
import com.khapp.suji.data.UserResponse
import com.khapp.suji.repository.UserRepository
import com.khapp.suji.utils.IconSet
import com.khapp.suji.view.comm.BaseViewModel

class UserViewModel(private val repository: UserRepository) : BaseViewModel() {
    companion object {
        const val DEFAULT_PHONE = "000-0000-0000"
    }

    val phone = MutableLiveData<String>("")
    val passwordValid = MutableLiveData<Boolean>(false)
    val user = repository.readLocalUser().asLiveData()

    fun numpadAction(num: String) {
        val current = phone.value
        current?.let {
            if (canAppend(it)) {
                appendPhone(num)
            }
        }
    }

    fun numpadDel() {
        val current = phone.value
        current?.let {
            if (it.isNotEmpty()) {
                deletePhone(it)
            }
        }
    }

    private fun deletePhone(current: String) {
        var new = current
        new = if (new.length == 5 || new.length == 10) {
            new.substring(0, new.length - 2)
        } else {
            new.substring(0, new.length - 1)
        }
        phone.postValue(new)
    }

    fun checkPhone(goto: (String, Int) -> Unit) {
        launchIO {
            val p = phone.value!!.replace("-", "")
            val code = repository.checkPhoneCode(p)
            launchUI { goto(p, code) }
        }
    }


    private fun canAppend(current: String): Boolean {
        return current.length < DEFAULT_PHONE.length
    }

    private fun appendPhone(num: String) {
        val current = phone.value
        current?.let {
            var new = it
            if (new.length == 3 || new.length == 8) {
                new = "$new-$num"
            } else {
                new += num
            }
            phone.postValue(new)
        }
    }

    fun checkPasswordValid(password: String) {
        val numRegex = Regex("[0-9]+")
        val alphaRegex = Regex("[a-zA-Z]+")
        passwordValid.postValue(
            password.length >= 8 && numRegex.containsMatchIn(password) && alphaRegex.containsMatchIn(
                password
            )
        )
    }

    fun passwordAction(
        phone: String,
        password: String,
        code: Int,
        success: () -> Unit,
        error: (Int, String) -> Unit,
        completed: () -> Unit
    ) {
        launchIO {
            val response = when (code) {
                CODE_USER_SIGNUP -> repository.signup(phone, password)
                CODE_USER_LOGIN -> repository.login(phone, password)
                else -> API.ERROR
            }
            launchUI {
                completed()
                if (response.code == CODE_SUCCESS) {
                    repository.saveUserLocal(response.data!!)
                    success()
                } else {
                    error(response.code, response.msg)
                }
            }
        }
    }

    fun logout() {
        //todo 二次确认,绑定的数据需要发生变化
        launchIO {
            repository.saveUserLocal(EMPTY_USER)
        }
    }

    fun loadAvatars() {
        if (IconSet.avatarsIcons.isEmpty())
            launchIO {
                IconSet.avatarsIcons = repository.loadAvatars()
            }
    }

    fun changeUserProfile(
        uid: Long,
        token: String,
        name: String,
        email: String,
        avatar: String,
        password: String = "",
        success: () -> Unit,
        error: (Int, String) -> Unit,
        completed: () -> Unit
    ) {
        launchIO {
            val response =
                repository.changeUserProfile(uid, token, name, email, avatar, password)
            launchUI { completed() }
            if (response.code == CODE_SUCCESS) {
                repository.saveUserLocal(response.data!!)
                launchUI { success() }
            } else {
                launchUI { error(response.code, response.msg) }
            }

        }
    }

    fun checkName(name: String, error: () -> Unit): Boolean {
        if (name.length < 3) {
            error()
            return false
        }
        return true
    }

    fun checkEmail(email: String, error: () -> Unit): Boolean {
        if (email.isEmpty()) return true
        val regex = Regex("[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+")
        if (!regex.containsMatchIn(email)) {
            error()
            return false
        }
        return true
    }


}


