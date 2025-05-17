package com.example.helloworld

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView

class SignInActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        val backArrow = findViewById<ImageView>(R.id.imageArrow)
        backArrow.setOnClickListener {
            finish()
        }

    }
}