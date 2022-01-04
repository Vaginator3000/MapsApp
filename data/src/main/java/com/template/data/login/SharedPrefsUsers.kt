package com.template.data.login

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.template.models.LoginUserModel

class SharedPrefsUsers(context: Context) {
    private val sPrefs = context.getSharedPreferences("data", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun isUserExist(loginOrEmail: String) : Boolean {
        getAllUsers().forEach { user ->
            if (user.email == loginOrEmail || user.login == loginOrEmail)
                return true
        }
        return false
    }

    fun authenticateByData(loginOrEmail: String, password: String) : Boolean {
        getAllUsers().forEach { user ->
            if ( user.password == password && (user.email == loginOrEmail || user.login == loginOrEmail) )
                return true
        }
        return false
    }

    fun authenticateBySession() : LoginUserModel? {
        val userInStr = sPrefs.getString("session", null) ?: return null

        val type = object : TypeToken<LoginUserModel>() {}.type
        val user: LoginUserModel = gson.fromJson(userInStr, type)

        return user
    }

    fun saveSession(user: LoginUserModel) {
        sPrefs.edit()
            .putString("session", gson.toJson(user))
            .apply()
    }

    fun addUser(user: LoginUserModel) {
        val users =
            getAllUsers() as MutableList<LoginUserModel>
        users.add(user)
        saveUsers(users)
    }

    private fun getAllUsers(): List<LoginUserModel> {
        val listInStr = sPrefs.getString("users", null) ?: return listOf()

        val type = object : TypeToken<List<LoginUserModel>>() {}.type
        val users: List<LoginUserModel> = gson.fromJson(listInStr, type)
        return  users
    }

    private fun saveUsers(users: List<LoginUserModel>) {
        sPrefs.edit()
            .putString("users", gson.toJson(users))
            .apply()
    }
}