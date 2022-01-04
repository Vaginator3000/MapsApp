package com.template.domain.login

interface Registration {
    fun addUser(login: String, email: String, password: String)
}