package com.example.keyboard_testapp.ui.keyboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun KeyboardLayout(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.LightGray,
    keyColor: Color = Color.Blue,
    textColor: Color = Color.White
) {
    val keyboardState = remember { mutableStateOf(KeyboardState.CAPS) }
    val keysArray = when(keyboardState.value) {
        KeyboardState.CAPS -> arrayOf(
            arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "0"),
            arrayOf("Й", "Ц", "У", "К", "Е", "Н", "Г", "Ш", "З", "Х"),
            arrayOf("Ф", "Ы", "В", "А", "П", "Р", "О", "Л", "Д", "Ж", "Э"),
            arrayOf("shift", "Я", "Ч", "С", "М", "И", "Т", "Ь", "Б", "Ю", "delete"),
            arrayOf("123", "em", ",", " ", ".", "enter")
        )
        KeyboardState.NOCAPS -> arrayOf(
            arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "0"),
            arrayOf("й", "ц", "у", "к", "е", "н", "г", "ш", "з", "х"),
            arrayOf("ф", "ы", "в", "а", "п", "р", "о", "л", "д", "ж", "э"),
            arrayOf("SHIFT", "я", "ч", "с", "м", "и", "т", "ь", "б", "ю", "delete"),
            arrayOf("123", "em", ",", " ", ".", "enter")
        )
        KeyboardState.DOUBLECAPS -> arrayOf(
            arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "0"),
            arrayOf("Й", "Ц", "У", "К", "Е", "Н", "Г", "Ш", "З", "Х"),
            arrayOf("Ф", "Ы", "В", "А", "П", "Р", "О", "Л", "Д", "Ж", "Э"),
            arrayOf("Shift", "Я", "Ч", "С", "М", "И", "Т", "Ь", "Б", "Ю", "delete"),
            arrayOf("123", "em", ",", " ", ".", "enter")
        )
        KeyboardState.NUMBER -> arrayOf(
            arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "0"),
            arrayOf("@", "#", "$", "%", "&", "-", "+", "(", ")"),
            arrayOf("=", "*", "\"", "'", ":", ";", "!", "?", "delete"),
            arrayOf("ABC", ",", "_", " ", "/", ".", "enter")
        )
        KeyboardState.EMOJI -> arrayOf(
            arrayOf("\uD83D\uDE00", "\uD83D\uDE22", "\uD83D\uDE09", "\uD83D\uDE1E", "\uD83D\uDE0D"),
            arrayOf("\uD83D\uDE03", "\uD83D\uDE06", "\uD83D\uDCA9", "\uD83D\uDD25", "\uD83D\uDE18"),
            arrayOf("ABC", ",", " ", ".", "delete")
        )
        else -> arrayOf(
            arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "0"),
            arrayOf("Й", "Ц", "У", "К", "Е", "Н", "Г", "Ш", "З", "Х"),
            arrayOf("Ф", "Ы", "В", "А", "П", "Р", "О", "Л", "Д", "Ж", "Э"),
            arrayOf("shift", "Я", "Ч", "С", "М", "И", "Т", "Ь", "Б", "Ю", "delete"),
            arrayOf("123", "em", ",", " ", ".", "enter")
        )
    }

    Column(
        modifier = modifier
            .background(backgroundColor)
            .fillMaxWidth()) {
        keysArray.forEach { row ->
            FixedHeightBox(
                modifier = modifier
                    .fillMaxWidth(),
                height = 60.dp) {
                Row(modifier) {
                    row.forEach { key ->
                        when(key) {
                            " " -> KeyboardKey(
                                keyboardKey = key,
                                modifier = modifier.weight(3.54f),
                                keyboardState = keyboardState,
                                keyColor = keyColor,
                                textColor = textColor
                            )
                            "enter" -> KeyboardKey(
                                keyboardKey = key,
                                modifier = modifier.weight(2f),
                                keyboardState = keyboardState,
                                keyColor = keyColor,
                                textColor = textColor
                            )
                            else -> KeyboardKey(
                                keyboardKey = key,
                                modifier = modifier.weight(1f),
                                keyboardState = keyboardState,
                                keyColor = keyColor,
                                textColor = textColor
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FixedHeightBox(
    modifier: Modifier,
    height: Dp,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) {
        measurables, constraints ->
        val placeables = measurables.map { measurable ->
            measurable.measure(constraints)
        }
        val _height = height.roundToPx()
        layout(constraints.minWidth, _height) {
            placeables.forEach { placeable ->
                placeable.place(
                    x = 0,
                    y = kotlin.math.min(0, _height - placeable.height)
                )
            }
        }
    }
}