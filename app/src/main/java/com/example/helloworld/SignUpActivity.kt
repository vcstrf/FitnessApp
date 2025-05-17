package com.example.helloworld
import com.example.helloworld.MyClickableSpan
import android.text.SpannableString
import android.widget.TextView
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.example.helloworld.R

class SignUpActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val backArrow = findViewById<ImageView>(R.id.imageArrow)
        backArrow.setOnClickListener {
            finish()
        }

        val tv = findViewById<TextView>(R.id.agreementText)
        val text = tv.text.toString()
        val spanstr = SpannableString(text)

        val firstTarget = "политикой конфиденциальности и обработки персональных данных"
        val secondTarget = "пользовательское соглашение"

        val firstStart = text.indexOf(firstTarget)
        val firstEnd = firstStart + firstTarget.length

        val secondStart = text.indexOf(secondTarget)
        val secondEnd = secondStart + secondTarget.length

        if (firstStart != -1 && secondStart != -1) {
            spanstr.setSpan(MyClickableSpan(this) {
            }, firstStart, firstEnd, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)

            spanstr.setSpan(MyClickableSpan(this) {
            }, secondStart, secondEnd, SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE)

            tv.text = spanstr
        }

    }
}

