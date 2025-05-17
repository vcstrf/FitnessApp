package com.example.helloworld
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View
import androidx.annotation.RequiresApi

class MyClickableSpan(private val context: Context, private val onClickAction: () -> Unit) : ClickableSpan() {
    override fun onClick(widget: View) {
        onClickAction()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun updateDrawState(ds: TextPaint) {
        super.updateDrawState(ds)
        ds.isUnderlineText = false
        ds.color = context.getColor(R.color.purple)
    }
}
