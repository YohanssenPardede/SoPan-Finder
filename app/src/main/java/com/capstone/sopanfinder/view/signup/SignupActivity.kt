package com.capstone.sopanfinder.view.signup

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.capstone.sopanfinder.R
import com.capstone.sopanfinder.databinding.ActivitySignupBinding
import com.capstone.sopanfinder.view.login.LoginActivity

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private val signupViewModel by viewModels<SignupViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        setAction()

        binding.tvToLogin.setOnClickListener {
            val intent = Intent(this@SignupActivity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }

    private fun setAction() {
        binding.edRegisterName.doOnTextChanged { _, _, _, count ->
            if (count == 0)
                binding.registerNameContainer.error = getString(R.string.null_name)
            else
                binding.registerNameContainer.error = null
        }

        binding.edRegisterEmail.doOnTextChanged { text, _, _, count ->
            if (count == 0)
                binding.registerEmailContainer.error = getString(R.string.null_email)
            else if (!Patterns.EMAIL_ADDRESS.matcher(text.toString()).matches())
                binding.registerEmailContainer.error = getString(R.string.invalid_email)
            else
                binding.registerEmailContainer.error = null
        }

        binding.edRegisterPassword.doOnTextChanged { text, _, _, count ->
            if (count == 0)
                binding.registerPasswordContainer.error = getString(R.string.null_password)
            else if (text!!.length < 8)
                binding.registerPasswordContainer.error = getString(R.string.minimal_characters)
            else
                binding.registerPasswordContainer.error = null
        }

        binding.btnSignup.setOnClickListener {
            val name = binding.edRegisterName.text.toString()
            val email = binding.edRegisterEmail.text.toString()
            val password = binding.edRegisterPassword.text.toString()
            val confirmPw = binding.edRegisterConfirm.text.toString()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                if (name.isEmpty()) {
                    binding.apply {
                        registerNameContainer.error = resources.getString(R.string.null_name)
                        edRegisterName.requestFocus()
                    }
                    Toast.makeText(this, getString(R.string.null_name), Toast.LENGTH_SHORT).show()
                } else
                    binding.registerNameContainer.error = null

                if (email.isEmpty()) {
                    binding.apply {
                        registerEmailContainer.error = resources.getString(R.string.null_email)
                        edRegisterEmail.requestFocus()
                    }
                    Toast.makeText(this, resources.getString(R.string.null_email), Toast.LENGTH_SHORT).show()
                } else if (email.isNotEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    binding.apply {
                        registerEmailContainer.error = getString(R.string.invalid_email)
                        edRegisterEmail.requestFocus()
                    }
                    Toast.makeText(this, resources.getString(R.string.invalid_email), Toast.LENGTH_SHORT).show()
                } else
                    binding.registerEmailContainer.error = null

                if (password.isEmpty()) {
                    binding.apply {
                        registerPasswordContainer.error = resources.getString(R.string.null_password)
                        edRegisterPassword.requestFocus()
                    }
                    Toast.makeText(this, resources.getString(R.string.null_password), Toast.LENGTH_SHORT).show()
                } else if (password.isNotEmpty() && password.length < 8) {
                    binding.apply {
                        registerPasswordContainer.error = getString(R.string.minimal_characters)
                        edRegisterPassword.requestFocus()
                    }
                    Toast.makeText(this, resources.getString(R.string.minimal_characters), Toast.LENGTH_SHORT).show()
                } else
                    binding.registerPasswordContainer.error = null

                return@setOnClickListener
            } else if (password.length < 8) {
                Toast.makeText(this, resources.getString(R.string.minimal_characters), Toast.LENGTH_SHORT).show()
                binding.apply {
                    registerPasswordContainer.error = getString(R.string.minimal_characters)
                    edRegisterPassword.requestFocus()
                }
                return@setOnClickListener
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                Toast.makeText(this, resources.getString(R.string.invalid_email), Toast.LENGTH_SHORT).show()
                binding.apply {
                    registerEmailContainer.error = getString(R.string.invalid_email)
                    edRegisterEmail.requestFocus()
                }
                return@setOnClickListener
            } else if (password != confirmPw) {
                Toast.makeText(this, getString(R.string.not_matched), Toast.LENGTH_SHORT).show()
                binding.apply {
                    registerConfirmContainer.error = getString(R.string.not_matched)
                    edRegisterConfirm.requestFocus()
                }
                return@setOnClickListener
            } else {
                binding.registerEmailContainer.error = null
                binding.registerNameContainer.error = null
                binding.registerPasswordContainer.error = null
                binding.registerConfirmContainer.error = null

                signupViewModel.register(email, name, password, confirmPw)
                signupViewModel.result.observe(this) {
                    Toast.makeText(this@SignupActivity, resources.getString(R.string.success_signup), Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@SignupActivity, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                    finish()
                }
                signupViewModel.isLoading.observe(this) {
                    showLoading(it)
                }
                signupViewModel.error.observe(this) {
                    if (!it)
                        Toast.makeText(this@SignupActivity, "Register Failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}