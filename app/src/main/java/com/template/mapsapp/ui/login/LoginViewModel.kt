package com.template.mapsapp.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.template.data.login.SharedPrefsAuthenticationImpl
import com.template.data.login.SharedPrefsRegistrationImpl
import com.template.models.LoginUserModel

class LoginViewModel(app: Application) : AndroidViewModel(app) {

    private val sharedPrefsAuthImpl by lazy { SharedPrefsAuthenticationImpl(context = getApplication<Application>().applicationContext) }
    private val sharedPrefsRegImpl by lazy { SharedPrefsRegistrationImpl(context = getApplication<Application>().applicationContext) }
   /*
   private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
    */

    fun authBySession() : LoginUserModel? {
        return sharedPrefsAuthImpl.authenticationBySession()
    }

    fun saveSession(loginOrEmail: String) {
        sharedPrefsAuthImpl.saveSession(loginOrEmail)
    }

    fun checkUserData(loginOrEmail: String, password: String) : Boolean {
        return sharedPrefsAuthImpl.authenticationByData(loginOrEmail = loginOrEmail, password = password)
    }

    fun addUser(user: LoginUserModel) {
        sharedPrefsRegImpl.addUser(user = user)
    }

    fun checkUserAlreadyExist(login: String, email: String) : Boolean {
        return sharedPrefsRegImpl.isUserExist(login, email)
    }

}