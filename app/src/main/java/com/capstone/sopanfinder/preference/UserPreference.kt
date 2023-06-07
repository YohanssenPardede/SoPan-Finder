package com.capstone.sopanfinder.preference

import android.content.Context
import com.capstone.sopanfinder.api.Login

class UserPreference private constructor(mContext: Context) {
    private val sharedPreference = mContext.getSharedPreferences(PREF, Context.MODE_PRIVATE)

    val isLoggedIn: Boolean
    get() {
        return sharedPreference.getString("token", null) != null
    }

    val user: Login
    get() {
        return Login(
            sharedPreference.getString("msg", null).toString(),
            sharedPreference.getString("name", null).toString(),
            sharedPreference.getInt("id", 0),
            sharedPreference.getString("token", null).toString(),
            sharedPreference.getString("email", null).toString()
        )
    }

    fun saveUser(user: Login) {
        val editor = sharedPreference.edit()

        editor.apply {
            putInt("id", user.id)
            putString("name", user.name)
            putString("email", user.email)
            putString("token", user.accessToken)
        }
        editor.apply()
    }

    fun clearSession() {
        val editor = sharedPreference.edit()
        editor.apply {
            clear()
        }
        editor.apply()
    }

    companion object {
        private const val PREF = "user_preference"
        private var mInstance: UserPreference? = null

        @Synchronized
        fun getInstance(mContext: Context): UserPreference {
            if (mInstance == null) {
                mInstance = UserPreference(mContext)
            }
            return mInstance as UserPreference
        }
    }
}