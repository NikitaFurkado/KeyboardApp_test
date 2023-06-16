package com.example.keyboard_testapp.ui.theme

import androidx.compose.ui.graphics.Color

fun String.stringToColor() : Color? {
    return when (this) {
        "Blue" -> Color.Blue
        "Red" -> Color.Red
        "White" -> Color.White
        "Black" -> Color.Black
        "Gray" -> Color.Gray
        else -> null
    }
}