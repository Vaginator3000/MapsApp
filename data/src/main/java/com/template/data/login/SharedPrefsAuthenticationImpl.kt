package com.template.data.login

import android.content.Context
import com.template.domain.login.Authentication
import com.template.models.LoginUserModel

class SharedPrefsAuthenticationImpl(context: Context) : Authentication {
    private val sharedPrefsDB = SharedPrefsDB(context)

    override fun authenticationByData(loginOrEmail: String, password: String) : Boolean {
        return sharedPrefsDB.authenticateByData(
            loginOrEmail = loginOrEmail,
            password = password
        )
    }

    override fun authenticationBySession() : LoginUserModel? {
        return sharedPrefsDB.authenticateBySession()
    }

    override fun saveSession(loginOrEmail: String) {
        return sharedPrefsDB.saveSession(loginOrEmail)
    }
}