package com.capstone.sopanfinder.view.login

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.capstone.sopanfinder.api.ApiConfig
import com.capstone.sopanfinder.api.Login
import com.capstone.sopanfinder.preference.UserPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    @SuppressLint("StaticFieldLeak")
    private val context = getApplication<Application>().applicationContext

    private val _user = MutableLiveData<Login>()
    val user: LiveData<Login> = _user

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> = _error

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun login(email: String, password: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().postLogin(email, password)
        client.enqueue(object : Callback<Login> {
            override fun onResponse(call: Call<Login>, response: Response<Login>) {
                val responseBody = response.body()

                _isLoading.value = false
                if (response.isSuccessful) {
                    Log.i("login token", responseBody!!.accessToken)
                    Log.i("login name", responseBody.name)

                    _user.value = response.body()
                    UserPreference.getInstance(context).saveUser(responseBody)
                } else {
                    _error.value = true
                    Log.e(TAG, "login onFailure \"onResponse\": ${response.body().toString()} & ${response.message()}")
                    Toast.makeText(context, "Email or Password is incorrect", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Login>, t: Throwable) {
                _isLoading.value = false
                _error.value = true
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    companion object {
        private const val TAG = "LoginViewModel"
    }
}