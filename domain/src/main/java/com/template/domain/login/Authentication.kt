package com.template.domain.login

import com.template.models.LoginUserModel

interface Authentication {
    fun authenticationByData(loginOrEmail: String, password: String) : Boolean
    fun authenticationBySession() : LoginUserModel?
    fun saveSession(loginOrEmail: String)
}