package com.template.data.login

import android.content.Context
import com.template.domain.login.Authentication
import com.template.domain.login.Registration
import com.template.models.LoginUserModel

class SharedPrefsRegistrationImpl(context: Context) : Registration {

    private val sharedPrefsUsers = SharedPrefsUsers(context)

    override fun addUser(user: LoginUserModel) {
        sharedPrefsUsers.addUser(user)
    }

    override fun isUserExist(login: String, email: String) : Boolean {
        return sharedPrefsUsers.isUserExist(login, email)
    }
}