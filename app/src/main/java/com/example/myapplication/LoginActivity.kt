package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var LoginButton: Button
    private lateinit var EmailET: EditText
    private lateinit var PasswordET: EditText
    private lateinit var RegButton: Button

    private var auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        if (auth.currentUser != null) {
            goToProfile()
        }
        setContentView(R.layout.activity_login)

        LoginButton = findViewById(R.id.Loginbutton)
        EmailET = findViewById(R.id.LoginEmailAddress)
        PasswordET = findViewById(R.id.LoginPassword)
        RegButton = findViewById(R.id.RegButton)

        registerListener()
    }

    private fun registerListener() {
        LoginButton.setOnClickListener {
            val email = EmailET.text.toString()
            val password = PasswordET.text.toString()
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    goToProfile()
                } else {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                }
            }
        }

        RegButton.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun goToProfile() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

}