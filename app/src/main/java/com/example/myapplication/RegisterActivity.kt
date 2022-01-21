package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.myapplication.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private lateinit var RButton : Button
    private lateinit var UserET : EditText
    private lateinit var EmailET : EditText
    private lateinit var PassET : EditText
    private lateinit var PassET1 : EditText
    
    private var auth = FirebaseAuth.getInstance()
    private var db = FirebaseDatabase.getInstance().getReference("users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.activity_register)

        RButton = findViewById(R.id.Rbutton)
        UserET = findViewById(R.id.UserName)
        EmailET = findViewById(R.id.EmailAddress)
        PassET = findViewById(R.id.Password)
        PassET1 = findViewById(R.id.Password1)

        registerListener()

    }

    private fun registerListener() {
        RButton.setOnClickListener {
            val email = EmailET.text.toString()
            val password = PassET.text.toString()

            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                }
            }.addOnSuccessListener {
                val username = UserET.text.toString()
                val user = User(auth.currentUser!!.uid, username)
                db.child(auth.currentUser!!.uid).setValue(user)
            }
        }
        
    }




}