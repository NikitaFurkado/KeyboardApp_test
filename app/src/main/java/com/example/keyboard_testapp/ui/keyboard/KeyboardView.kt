package com.example.keyboard_testapp.ui.keyboard

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.AbstractComposeView
import com.example.keyboard_testapp.ui.theme.stringToColor

class KeyboardView(context: Context) : AbstractComposeView(context) {

    @Composable
    override fun Content() {
        val prev = context.getSharedPreferences("keyboard", Context.MODE_PRIVATE)
        val background = prev.getString("background", "White")
        val key = prev.getString("key", "White")
        val text = prev.getString("text", "Black")
        val backColor = background?.stringToColor()!!
        val keyColor = key?.stringToColor()!!
        val textColor = text?.stringToColor()!!

        KeyboardLayout(
            backgroundColor = backColor,
            keyColor = keyColor,
            textColor = textColor
        )
    }
}