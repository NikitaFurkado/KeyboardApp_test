package com.example.keyboard_testapp.ui.keyboard

import android.content.Context
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.inputmethod.InputConnection
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.keyboard_testapp.KeyboardService
import com.example.keyboard_testapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun KeyboardKey(
    keyboardKey: String,
    modifier: Modifier,
    keyboardState: MutableState<KeyboardState>,
    keyColor: Color = Color.Blue,
    textColor: Color = Color.Black
) {
    val interactionSource = remember { MutableInteractionSource() }
    val pressed = interactionSource.collectIsPressedAsState()
    val context = LocalContext.current
    val iconMap = hashMapOf(
        "em" to R.drawable.emoji,
        "shift" to R.drawable.shift_dt,
        "Shift" to R.drawable.shift_dt,
        "SHIFT" to R.drawable.shift,
        "delete" to R.drawable.delete,
        "enter" to R.drawable.enter,
        " " to R.drawable.space,
        "123" to R.drawable.baseline_123,
        "ABC" to R.drawable.baseline_abc
    )
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(4.dp),
        contentAlignment = Alignment.BottomCenter
    ){
        Card(
            elevation = 5.dp,
            modifier = modifier,
            backgroundColor = keyColor
        ) {
            if (iconMap.containsKey(keyboardKey)) {
                iconMap[keyboardKey]?.let {
                    KeyboardIcon(
                        context = context,
                        modifier = modifier,
                        keyboardKey = keyboardKey,
                        keyboardState = keyboardState,
                        drawable = it,
                        textColor = textColor
                    )
                }
            } else {
                Text(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(2.dp)
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) {
                            (context as KeyboardService)
                                .currentInputConnection
                                .commitText(keyboardKey, keyboardKey.length)
                        }
                        .background(keyColor)
                        .padding(
                            top = 14.dp,
                            bottom = 14.dp
                        ),
                    text = keyboardKey,
                    textAlign = TextAlign.Center,
                    color = textColor,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                if (pressed.value) {
                    Text(
                        modifier = modifier
                            .fillMaxWidth()
                            .background(keyColor)
                            .padding(top = 16.dp, bottom = 16.dp),
                        text = keyboardKey,
                        textAlign = TextAlign.Center,
                        color = textColor,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun KeyboardIcon(
    context: Context,
    modifier: Modifier = Modifier,
    keyboardKey: String,
    drawable: Int,
    keyboardState: MutableState<KeyboardState>,
    textColor: Color = Color.Black
) {
    val currentInputConnection = (context as KeyboardService).currentInputConnection
    Icon(
        painter = painterResource(id = drawable),
        stringResource(id = R.string.key_icon),
        tint = textColor,
        modifier = modifier
            .fillMaxWidth()
            .pointerInteropFilter {
                when (it.action) {
                    MotionEvent.ACTION_DOWN -> whenKeyClick(
                        motionEvent = it,
                        keyboardKey = keyboardKey,
                        keyboardState = keyboardState,
                        currentInputConnection = currentInputConnection
                    )
                }
                true
            }
            .padding(2.dp)
            .padding(
                top = 14.dp,
                bottom = 14.dp
            ),
    )
}

fun whenKeyClick(
    motionEvent: MotionEvent,
    keyboardKey: String,
    keyboardState: MutableState<KeyboardState>,
    currentInputConnection: InputConnection
) {
    when (keyboardKey) {
        " " -> {currentInputConnection.sendKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_SPACE))}

        "Shift" -> {
            keyboardState.value = KeyboardState.DOUBLECAPS
        }
        "SHIFT" -> {
            keyboardState.value = KeyboardState.CAPS
        }
        "shift" -> {
            keyboardState.value = KeyboardState.NOCAPS
        }
        "delete" -> {
            CoroutineScope(Dispatchers.IO).launch {
                while (motionEvent.action == MotionEvent.ACTION_DOWN) {
                    delay(100)
                    currentInputConnection.sendKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL))
                }
            }
        }
        "enter" -> {currentInputConnection.sendKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER))}
        "123" -> keyboardState.value = KeyboardState.NUMBER
        "ABC" -> keyboardState.value = KeyboardState.STRING
        "em" -> keyboardState.value = KeyboardState.EMOJI
    }
}