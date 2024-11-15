package com.netomi.sampleapplication.ui

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.StyleSpan
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.netomi.sampleapplication.R

class LoginActivity : AppCompatActivity() {

    private lateinit var emailEditText: AppCompatEditText
    private lateinit var passwordEditText: AppCompatEditText
    private lateinit var signupTextView: AppCompatTextView
    private lateinit var forgotPasswordTextView: AppCompatTextView
    private lateinit var emailValid: AppCompatTextView
    private lateinit var passwordValid: AppCompatTextView
    private lateinit var passwordTextInputLayout: ConstraintLayout
    private lateinit var emailTextInputLayout: ConstraintLayout
    private lateinit var loginButton: AppCompatButton
    private lateinit var passwordToggle: AppCompatImageView

    private var isPasswordVisible = false
    private var emailValidB = false
    private var passwordValidB = false

    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    private val passwordPattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}$"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        initViews() // Initialize all views
        setupFocusListeners() // Set up focus listeners for email and password fields
        setupTextWatchers() // Set up text watchers for validation
        setupPasswordToggle() // Set up password visibility toggle
        spannableString() // Set up the clickable "Create account" text
        enableButton() // Enable/disable login button based on validations
    }

    // Initialize all the views
    private fun initViews() {
        signupTextView = findViewById(R.id.signupTextView)
        forgotPasswordTextView = findViewById(R.id.forgotPasswordTextView)
        emailValid = findViewById(R.id.emailValidTextview)
        passwordValid = findViewById(R.id.passwordValidTextview)
        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        emailTextInputLayout = findViewById(R.id.emailTextInputLayout)
        passwordTextInputLayout = findViewById(R.id.passwordTextInputLayout)
        loginButton = findViewById(R.id.loginButton)
        passwordToggle = findViewById(R.id.passwordToggle)

        // Set login button click listener
        loginButton.setOnClickListener { startActivity(Intent(this, HomeActivity::class.java)) }
    }

    // Set focus change listeners to update the background based on focus state
    private fun setupFocusListeners() {
        setFocusChangeListener(emailEditText, emailTextInputLayout)
        setFocusChangeListener(passwordEditText, passwordTextInputLayout)
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
        emailValidB = emailEditText.text.toString().matches(emailPattern.toRegex())
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
        passwordValidB = passwordEditText.text.toString().matches(passwordPattern.toRegex())
        passwordValid.visibility = if (passwordValidB) View.GONE else View.VISIBLE
        passwordValid.text = if (passwordValidB) "" else getString(R.string.enter_valid_password)
        passwordTextInputLayout.setBackgroundResource(
            if (passwordValidB) R.drawable.rounded_active_edittext_background
            else R.drawable.rounded_error_edittext_background
        )
        enableButton() // Enable button if both email and password are valid
    }

    // Enable or disable the login button based on validation states
    private fun enableButton() {
        loginButton.isEnabled = emailValidB && passwordValidB
        loginButton.alpha = if (loginButton.isEnabled) 1f else 0.5f
    }

    // Handle password visibility toggle (show/hide password)
    private fun setupPasswordToggle() {
        passwordToggle.setOnClickListener {
            isPasswordVisible = !isPasswordVisible
            passwordEditText.inputType = if (isPasswordVisible) {
                InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else {
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
            passwordToggle.setImageResource(
                if (isPasswordVisible) R.drawable.ic_eye_view else R.drawable.ic_eye_hide
            )
            passwordEditText.setSelection(passwordEditText.text?.length ?: 0) // Keep cursor at the end
        }
    }

    // Set up the clickable "Create account" text
    private fun spannableString() {
        val text = "Don’t have an account? Create account"
        val spannableString = SpannableString(text)

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                Toast.makeText(this@LoginActivity, "Create Account Clicked", Toast.LENGTH_SHORT).show()
            }

            // Remove underline and set custom color for the clickable text
            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = ContextCompat.getColor(this@LoginActivity, R.color.black)
                ds.isUnderlineText = false
            }
        }

        // Make "Create account" bold
        spannableString.setSpan(StyleSpan(Typeface.BOLD), 22, text.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(clickableSpan, 22, text.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        signupTextView.text = spannableString
        signupTextView.movementMethod = LinkMovementMethod.getInstance() // Enable clickable link behavior
    }
}
