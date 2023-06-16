package com.example.keyboard_testapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.keyboard_testapp.ui.screen.TestScreen
import com.example.keyboard_testapp.ui.theme.Keyboard_testappTheme

class TestActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Keyboard_testappTheme {
                TestScreen()
            }
        }
    }
}