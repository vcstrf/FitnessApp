package com.example.helloworld

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.google.android.material.button.MaterialButton

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

    val signUpButton = findViewById<MaterialButton>(R.id.button_sign_up)
    val signInButton = findViewById<MaterialButton>(R.id.button_sign_in)

    signUpButton.setOnClickListener {
        val intent = Intent(this, SignUpActivity::class.java)
        startActivity(intent)
    }

    signInButton.setOnClickListener {
        val intent = Intent(this, SignInActivity::class.java)
        startActivity(intent)
    }
}
}