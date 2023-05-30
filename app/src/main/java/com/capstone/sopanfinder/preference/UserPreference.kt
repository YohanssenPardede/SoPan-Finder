package com.capstone.sopanfinder.preference

import android.content.Context
import com.capstone.sopanfinder.api.User

class UserPreference private constructor(mContext: Context) {
    private val sharedPreference = mContext.getSharedPreferences(PREF, Context.MODE_PRIVATE)

    val isLoggedIn: Boolean
    get() {
        return sharedPreference.getString("token", null) != null
    }

    val user: User
    get() {
        return User(
            sharedPreference.getString("userId", null).toString(),
            sharedPreference.getString("name", null).toString(),
            sharedPreference.getString("token", null).toString()
        )
    }

    fun saveUser(user: User) {
        val editor = sharedPreference.edit()

        editor.apply {
            putString("userId", user.userId)
            putString("name", user.name)
            putString("token", user.token)
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