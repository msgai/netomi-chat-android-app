package com.netomi.sampleapplication.ui.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.netomi.sampleapplication.R
import com.netomi.sampleapplication.constant.AppConstant
import com.netomi.sampleapplication.constant.SharePreferenceConstant
import com.netomi.sampleapplication.utils.AppSharedPreferences

class SignupActivity : AppCompatActivity() {
    private lateinit var emailEditText: AppCompatEditText
    private lateinit var passwordEditText: AppCompatEditText
    private lateinit var nameEditText: AppCompatEditText
    private lateinit var phoneEditText: AppCompatEditText

    private lateinit var signupTextView: AppCompatTextView
    private lateinit var loginTextView: AppCompatTextView

    private lateinit var emailValid: AppCompatTextView
    private lateinit var passwordValid: AppCompatTextView

    private lateinit var nameValid: AppCompatTextView
    private lateinit var phoneValid: AppCompatTextView

    private lateinit var passwordTextInputLayout: ConstraintLayout
    private lateinit var emailTextInputLayout: ConstraintLayout

    private lateinit var phoneTextInputLayout: ConstraintLayout
    private lateinit var nameTextInputLayout: ConstraintLayout

    private lateinit var createButton: AppCompatButton
    private lateinit var passwordToggle: AppCompatImageView

    private var isPasswordVisible = false
    private var emailValidB = false
    private var passwordValidB = false
    private var nameValidB = false
    private var phoneValidB = false

    private lateinit var preferences : AppSharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        preferences = AppSharedPreferences(this)
        setContentView(R.layout.activity_signup)

        initViews() // Initialize all views
        setupFocusListeners() // Set up focus listeners for email and password fields
        setupTextWatchers() // Set up text watchers for validation
        setupPasswordToggle() // Set up password visibility toggle
        enableButton() // Enable/disable login button based on validations

    }

    // Initialize all the views
    private fun initViews() {
        loginTextView = findViewById(R.id.loginTextView)
        signupTextView = findViewById(R.id.signupTextView)

        emailValid = findViewById(R.id.emailValidTextview)
        passwordValid = findViewById(R.id.passwordValidTextview)

        nameValid = findViewById(R.id.nameValidTextview)
        phoneValid = findViewById(R.id.phoneValidTextview)

        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)

        nameEditText = findViewById(R.id.nameEditText)
        phoneEditText = findViewById(R.id.phoneEditText)

        emailTextInputLayout = findViewById(R.id.emailTextInputLayout)
        passwordTextInputLayout = findViewById(R.id.passwordTextInputLayout)

        nameTextInputLayout = findViewById(R.id.nameTextInputLayout)
        phoneTextInputLayout = findViewById(R.id.phoneTextInputLayout)

        createButton = findViewById(R.id.createButton)
        passwordToggle = findViewById(R.id.passwordToggle)

        // Set create button click listener
        createButton.setOnClickListener {
            preferences.setBoolean(SharePreferenceConstant.LOGIN,true)
            preferences.setString(SharePreferenceConstant.NAME,nameEditText.text?.trim().toString())
            preferences.setString(SharePreferenceConstant.EMAIL,emailEditText.text?.trim().toString())
            startActivity(Intent(this, HomeActivity::class.java))
            finishAffinity()
        }

        loginTextView.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finishAffinity()
        }
    }

    // Set focus change listeners to update the background based on focus state
    private fun setupFocusListeners() {
        setFocusChangeListener(emailEditText, emailTextInputLayout)
        setFocusChangeListener(passwordEditText, passwordTextInputLayout)

        setFocusChangeListener(nameEditText, nameTextInputLayout)
        setFocusChangeListener(phoneEditText, phoneTextInputLayout)
    }

    // Helper function for setting focus listeners
    private fun setFocusChangeListener(editText: AppCompatEditText, layout: ConstraintLayout) {
        editText.setOnFocusChangeListener { _, hasFocus ->
            layout.setBackgroundResource(
                if (hasFocus) R.drawable.rounded_active_edittext_background
                else R.drawable.rounded_edittext_background
            )
        }
    }

    // Set up text watchers for email and password fields
    private fun setupTextWatchers() {
        emailEditText.addTextChangedListener(createTextWatcher(::validateEmail))
        passwordEditText.addTextChangedListener(createTextWatcher(::validatePassword))
        nameEditText.addTextChangedListener(createTextWatcher(::validateName))
        phoneEditText.addTextChangedListener(createTextWatcher(::validatePhoneNumber))
    }

    // Helper function to create TextWatcher with a validation function
    private fun createTextWatcher(validationFunc: () -> Unit): TextWatcher {
        return object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = validationFunc()
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }
    }

    // Validate email format and update UI accordingly
    private fun validateEmail() {
        emailValidB = emailEditText.text.toString().matches(AppConstant.emailPattern.toRegex())
        emailValid.visibility = if (emailValidB) View.GONE else View.VISIBLE
        emailValid.text = if (emailValidB) "" else getString(R.string.invalid_email_format)
        emailTextInputLayout.setBackgroundResource(
            if (emailValidB) R.drawable.rounded_active_edittext_background
            else R.drawable.rounded_error_edittext_background
        )
        enableButton() // Enable button if both email and password are valid
    }

    // Validate password format and update UI accordingly
    private fun validatePassword() {
        passwordValidB = passwordEditText.text.toString().matches(AppConstant.passwordPattern.toRegex())
        passwordValid.visibility = if (passwordValidB) View.GONE else View.VISIBLE
        passwordValid.text = if (passwordValidB) "" else getString(R.string.enter_valid_password)
        passwordTextInputLayout.setBackgroundResource(
            if (passwordValidB) R.drawable.rounded_active_edittext_background
            else R.drawable.rounded_error_edittext_background
        )
        enableButton() // Enable button if both email and password are valid
    }

    // Validate name format and update UI accordingly
    private fun validateName() {
        val nameText = nameEditText.text.toString()
        nameValidB = nameText.isNotBlank() && nameText.length >= 2 // Add specific rules as needed
        nameValid.visibility = if (nameValidB) View.GONE else View.VISIBLE
        nameValid.text = if (nameValidB) "" else getString(R.string.enter_your_name)
        nameTextInputLayout.setBackgroundResource(
            if (nameValidB) R.drawable.rounded_active_edittext_background
            else R.drawable.rounded_error_edittext_background
        )
        enableButton() // Enable button if all fields are valid
    }

    // Validate phone number format and update UI accordingly
    private fun validatePhoneNumber() {
        val phoneText = phoneEditText.text.toString()
        phoneValidB = phoneText.matches(AppConstant.phonePattern.toRegex()) // Define a pattern in AppConstant
        phoneValid.visibility = if (phoneValidB) View.GONE else View.VISIBLE
        phoneValid.text = if (phoneValidB) "" else getString(R.string.invalid_phone_format)
        phoneTextInputLayout.setBackgroundResource(
            if (phoneValidB) R.drawable.rounded_active_edittext_background
            else R.drawable.rounded_error_edittext_background
        )
        enableButton() // Enable button if all fields are valid
    }

    // Enable or disable the login button based on validation states
    private fun enableButton() {
        createButton.isEnabled = emailValidB && passwordValidB && phoneValidB &&nameValidB
        createButton.alpha = if (createButton.isEnabled) 1f else 0.5f
    }

    // Handle password visibility toggle (show/hide password)
    private fun setupPasswordToggle() {
        passwordToggle.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            if (passwordEditText.text?.isNotEmpty() == true) {
                passwordEditText.inputType = if (isPasswordVisible) {
                    InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                } else {
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                }
            }
            passwordToggle.setImageResource(
                if (isPasswordVisible) R.drawable.ic_eye_view else R.drawable.ic_eye_hide
            )
            passwordEditText.setSelection(passwordEditText.text?.length ?: 0) // Keep cursor at the end
        }
    }
}