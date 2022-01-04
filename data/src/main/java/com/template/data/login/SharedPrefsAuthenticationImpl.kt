package com.template.data.login

import android.content.Context
import com.template.domain.login.Authentication
import com.template.models.LoginUserModel

class SharedPrefsAuthenticationImpl(context: Context) : Authentication {
    private val sharedPrefsUsers = SharedPrefsUsers(context)

    override fun authenticationByData(loginOrEmail: String, password: String) : Boolean {
        return sharedPrefsUsers.authenticateByData(
            loginOrEmail = loginOrEmail,
            password = password
        )
    }

    override fun authenticationBySession() : LoginUserModel? {
        return sharedPrefsUsers.authenticateBySession()
    }
}