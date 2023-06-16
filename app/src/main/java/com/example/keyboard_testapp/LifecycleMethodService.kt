package com.example.keyboard_testapp

import android.content.Intent
import android.inputmethodservice.InputMethodService
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ServiceLifecycleDispatcher

abstract class LifecycleMethodService : InputMethodService(), LifecycleOwner {

    val disp =ServiceLifecycleDispatcher(this)

    override fun onCreate() {
        disp.onServicePreSuperOnCreate()
        super.onCreate()
    }

    override fun onBindInput() {
        super.onBindInput()
        disp.onServicePreSuperOnBind()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        disp.onServicePreSuperOnDestroy()
        super.onDestroy()
    }
}