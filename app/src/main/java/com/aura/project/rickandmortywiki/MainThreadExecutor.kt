package com.aura.project.rickandmortywiki

import android.os.Looper
import java.util.concurrent.Executor

class MainThreadExecutor : Executor {
    private val _handler = android.os.Handler(Looper.getMainLooper())
    override fun execute(command: Runnable?) {
        _handler.post(command)
    }
}