package com.example.keyboard_testapp.ui.screen

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.keyboard_testapp.R

@Composable
fun TestScreen(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(4.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var text by rememberSaveable {
            mutableStateOf("")
        }
        TextField(
            value = text,
            onValueChange = { text = it },
            modifier = modifier.fillMaxWidth(),
            label = { Text(text = stringResource(R.string.enter_something))}
        )
        Button(
            modifier = modifier.fillMaxWidth(),
            onClick = {
                val prev = context.getSharedPreferences("keyboard_color", Context.MODE_PRIVATE)
                val editor = prev.edit()
                editor.putString("background", "Gray")
                editor.putString("key", "Blue")
                editor.putString("text", "White")
                editor.apply()
            },
            shape = RoundedCornerShape(50)
        ) {
            Text(text = stringResource(R.string.change_color))
        }
    }
}