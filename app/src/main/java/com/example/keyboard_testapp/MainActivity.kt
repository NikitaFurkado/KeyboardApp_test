package com.example.keyboard_testapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.inputmethod.InputMethodManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.example.keyboard_testapp.ui.screen.MainScreen
import com.example.keyboard_testapp.ui.theme.Keyboard_testappTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val context = LocalContext.current
            val manager =
                context.applicationContext
                    .getSystemService(INPUT_METHOD_SERVICE)
                        as InputMethodManager
            Keyboard_testappTheme {
                MainScreen(
                    onTestClicked = {
                        ContextCompat.startActivity(
                        this,
                            Intent(
                                this,
                                TestActivity::class.java
                            ),
                        null
                    ) },
                    onButtonClicked = {
                        if (isThisKeyboardSetDefaultIME(context, manager)) {
                            context.startActivity(
                                Intent(Settings.ACTION_INPUT_METHOD_SETTINGS)
                            )
                        }
                    },
                    onInstallClicked = {
                        manager.showInputMethodPicker()
                    }
                )
            }
        }
    }

    private fun isThisKeyboardSetDefaultIME(context: Context, manager: InputMethodManager) : Boolean {
        var bool = true
        val list = manager.enabledInputMethodList
        list.forEach {
            if (it.packageName == context.packageName)
                bool = false
        }
        return bool
    }
}