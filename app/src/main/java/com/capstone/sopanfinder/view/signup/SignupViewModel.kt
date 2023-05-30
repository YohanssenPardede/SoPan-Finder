package com.capstone.sopanfinder.view.signup

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.sopanfinder.api.ApiConfig
import com.capstone.sopanfinder.api.Register
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupViewModel : ViewModel() {
    private val _result = MutableLiveData<Register>()
    val result: LiveData<Register> = _result

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> = _error

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun register(name: String, email: String, password: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().postRegister(name, email, password)
        client.enqueue(object : Callback<Register> {
            @SuppressLint("LongLogTag")
            override fun onResponse(call: Call<Register>, response: Response<Register>) {
                val responseBody = response.body()

                _isLoading.value = false
                if (response.isSuccessful) {
                    Log.i("signup name, email, password", "$name || $email || $password")
                    _result.value = responseBody!!
                } else {
                    _error.value = true
                    Log.e(TAG, "register onFailure \"onResponse\": ${response.body().toString()} & ${response.message()}")
                }
            }

            override fun onFailure(call: Call<Register>, t: Throwable) {
                _isLoading.value = false
                _error.value = true
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    companion object {
        private const val TAG = "SignupViewModel"
    }
}