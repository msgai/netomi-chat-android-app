package com.netomi.sampleapplication

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.Editable
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.text.style.BackgroundColorSpan
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.RecyclerView
import com.netomi.chat.model.NCWMessage
import com.netomi.chat.ui.view.ChatAdapter

class LoginActivity : AppCompatActivity() {

    private lateinit var emailEditText: AppCompatEditText
    private lateinit var passwordEditText: AppCompatEditText

    private lateinit var signupTextView: AppCompatTextView
    private lateinit var forgotPasswordTextView: AppCompatTextView
    private lateinit var emailValid: AppCompatTextView
    private lateinit var passwordValid: AppCompatTextView

    private lateinit var passwordTextInputLayout: LinearLayout
    private lateinit var emailTextInputLayout: LinearLayout
    private lateinit var loginButton: AppCompatButton

    // Regular expression for validating email format
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    private val passwordPattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}$"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        signupTextView = findViewById(R.id.signupTextView)
        forgotPasswordTextView = findViewById(R.id.forgotPasswordTextView)

        emailValid = findViewById(R.id.emailValidTextview)
        passwordValid = findViewById(R.id.passwordValidTextview)

        emailEditText = findViewById(R.id.emailEditText)
        passwordEditText = findViewById(R.id.passwordEditText)

        emailTextInputLayout = findViewById(R.id.emailTextInputLayout)
        passwordTextInputLayout = findViewById(R.id.passwordTextInputLayout)
        loginButton = findViewById(R.id.loginButton)

        emailEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                // Set the background for when the EditText is focused
                emailTextInputLayout.setBackgroundResource(R.drawable.rounded_active_edittext_background)
            } else {
                // Set the background for when the EditText is not focused
                emailTextInputLayout.setBackgroundResource(R.drawable.rounded_edittext_background)
            }
        }

        passwordEditText.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                // Set the background for when the EditText is focused
                passwordTextInputLayout.setBackgroundResource(R.drawable.rounded_active_edittext_background)
            } else {
                // Set the background for when the EditText is not focused
                passwordTextInputLayout.setBackgroundResource(R.drawable.rounded_edittext_background)
            }
        }

        loginButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

        // TextWatcher for validating email
        emailEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val email = s.toString()
                if (email.matches(emailPattern.toRegex())) {
                    emailValid.visibility = View.GONE
                    emailTextInputLayout.setBackgroundResource(R.drawable.rounded_active_edittext_background)
                } else {
                    emailValid.visibility = View.VISIBLE
                    emailValid.text = getString(R.string.invalid_email_format)
                    emailTextInputLayout.setBackgroundResource(R.drawable.rounded_error_edittext_background)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // TextWatcher for validating password
        passwordEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val password = s.toString()
                if (password.matches(passwordPattern.toRegex())) {
                    passwordValid.visibility = View.GONE
                    passwordTextInputLayout.setBackgroundResource(R.drawable.rounded_active_edittext_background)
                } else {
                    passwordValid.visibility = View.VISIBLE
                    passwordValid.text = getString(R.string.enter_valid_password)
                    passwordTextInputLayout.setBackgroundResource(R.drawable.rounded_error_edittext_background)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        spannableString()
    }

    private fun spannableString() {

        val text = "Don’t have an account? Create account"
        val spannableString = SpannableString(text)

        // Make "Create account" clickable
        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                // Handle the click event here
                Toast.makeText(this@LoginActivity, "Create Account Clicked", Toast.LENGTH_SHORT).show()
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                ds.color = ContextCompat.getColor(this@LoginActivity, R.color.black)
                ds.isUnderlineText = false
            }
        }

        // Set "Create account" text in bold
        val boldSpan = StyleSpan(Typeface.BOLD)
        spannableString.setSpan(boldSpan, 22, text.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        spannableString.setSpan(clickableSpan, 22, text.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        signupTextView.text = spannableString
        signupTextView.movementMethod = LinkMovementMethod.getInstance() // Makes links clickable
    }
}