package com.capstone.sopanfinder.view.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import com.capstone.sopanfinder.R
import com.capstone.sopanfinder.databinding.ActivityLoginBinding
import com.capstone.sopanfinder.view.ViewModelFactory
import com.capstone.sopanfinder.view.home.HomeActivity
import com.capstone.sopanfinder.view.signup.SignupActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        loginViewModel = setViewModel(this)
        setAction()

        binding.tvToSignup.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignupActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }

    private fun setViewModel(activity: AppCompatActivity): LoginViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[LoginViewModel::class.java]
    }

    private fun setAction() {
        binding.edLoginEmail.doOnTextChanged { text, _, _, count ->
            if (count == 0)
                binding.loginEmailContainer.error = getString(R.string.null_email)
            else if (!Patterns.EMAIL_ADDRESS.matcher(text.toString()).matches())
                binding.loginEmailContainer.error = getString(R.string.invalid_email)
            else
                binding.loginEmailContainer.error = null
        }

        binding.edLoginPassword.doOnTextChanged { text, _, _, count ->
            if (count == 0)
                binding.loginPasswordContainer.error = getString(R.string.null_password)
            else if (text!!.length < 8)
                binding.loginPasswordContainer.error = getString(R.string.minimal_characters)
            else
                binding.loginPasswordContainer.error = null
        }

        binding.btnSignup.setOnClickListener {
            val email = binding.edLoginEmail.text.toString()
            val password = binding.edLoginPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                if (email.isEmpty()) {
                    binding.apply {
                        loginEmailContainer.error = resources.getString(R.string.null_email)
                        edLoginEmail.requestFocus()
                    }
                    Toast.makeText(this, resources.getString(R.string.null_email), Toast.LENGTH_SHORT).show()
                } else if (email.isNotEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    binding.apply {
                        loginEmailContainer.error = getString(R.string.invalid_email)
                        edLoginEmail.requestFocus()
                    }
                    Toast.makeText(this, resources.getString(R.string.invalid_email), Toast.LENGTH_SHORT).show()
                } else
                    binding.loginEmailContainer.error = null

                if (password.isEmpty()) {
                    binding.apply {
                        loginPasswordContainer.error = resources.getString(R.string.null_password)
                        edLoginPassword.requestFocus()
                    }
                    Toast.makeText(this, resources.getString(R.string.null_password), Toast.LENGTH_SHORT).show()
                } else if (password.isNotEmpty() && password.length < 8) {
                    binding.apply {
                        loginPasswordContainer.error = getString(R.string.minimal_characters)
                        edLoginPassword.requestFocus()
                    }
                    Toast.makeText(this, resources.getString(R.string.minimal_characters), Toast.LENGTH_SHORT).show()
                } else
                    binding.loginPasswordContainer.error = null

                return@setOnClickListener
            } else if (password.length < 8) {
                Toast.makeText(this, resources.getString(R.string.minimal_characters), Toast.LENGTH_SHORT).show()
                binding.apply {
                    loginPasswordContainer.error = getString(R.string.minimal_characters)
                    edLoginPassword.requestFocus()
                }
                return@setOnClickListener
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, resources.getString(R.string.invalid_email), Toast.LENGTH_SHORT).show()
                binding.apply {
                    loginEmailContainer.error = getString(R.string.invalid_email)
                    edLoginEmail.requestFocus()
                }
                return@setOnClickListener
            } else {
                binding.loginEmailContainer.error = null
                binding.loginPasswordContainer.error = null

                loginViewModel.login(email, password)
                loginViewModel.user.observe(this) { user ->
                    Log.d(TAG, "${user.accessToken} || ${user.id} || ${user.name} || ${user.email}")

                    Toast.makeText(this@LoginActivity, getString(R.string.success_login, user.name), Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()
                }
                loginViewModel.isLoading.observe(this) {
                    showLoading(it)
                }
                loginViewModel.error.observe(this) {
                    if (it)
                        Toast.makeText(this@LoginActivity, resources.getString(R.string.login_failed), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    companion object {
        private const val TAG = "LoginActivity"
    }
}