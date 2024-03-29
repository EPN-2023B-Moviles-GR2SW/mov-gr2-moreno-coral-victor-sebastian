package com.sirajlekhak.chatapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LogIn : AppCompatActivity() {

    private lateinit var edtEmail: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnSignUp: Button

    private lateinit var mAuth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)

        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()

        edtEmail = findViewById(R.id.edt_email)
        edtPassword = findViewById(R.id.edt_password)
        btnLogin = findViewById(R.id.btnLogin)
        btnSignUp = findViewById(R.id.btnSignUp)

        btnSignUp.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener {
            val email = edtEmail.text.toString()
            val password = edtPassword.text.toString()

            login(email, password)
        }

    }

    private fun login(email: String, password: String) {
        //login for logging user

                    //code for logging in user
                    Toast.makeText(applicationContext, "Login is successful", Toast.LENGTH_SHORT)
                        .show()
                    val intent = Intent(this@LogIn, MainActivity::class.java)
                    finish()
                    startActivity(intent)

    }

    override fun onStart() {
        super.onStart()

        val user = mAuth.currentUser

        if (user != null) {

            val intent = Intent(this@LogIn, MainActivity::class.java)
            finish()
            startActivity(intent)

        }

    }
}