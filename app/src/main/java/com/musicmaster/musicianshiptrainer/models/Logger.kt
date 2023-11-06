package com.musicmaster.musicianshiptrainer.models

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import java.util.UUID

data class LogMessage(val id: UUID = UUID.randomUUID(), val number: Int, val message: String)

class Logger : ViewModel() {

    companion object {
        fun error(s: String) {
        }
        val instance = Logger()
    }

    // Equivalent of @Published in Swift
    var loggedMsg by mutableStateOf<String?>(null)
    var errorNo by mutableStateOf(0)
    var errorMsg by mutableStateOf<String?>(null)

    private val recordedMsgs = mutableListOf<LogMessage>()

    fun reportError(context: String, err: Exception? = null) {
        var msg = "🛑 *** ERROR *** ErrNo: $errorNo: $context"
        err?.let {
            msg += ", ${it.localizedMessage}"
        }
        println(msg)
        recordedMsgs.add(LogMessage(number = recordedMsgs.size, message = msg))
        // Updating the state must be done on the main thread
        // Consider using a coroutine or LiveData to handle this
        errorMsg = msg
        errorNo += 1
    }

    fun reportErrorString(context: String, err: Exception? = null) {
        reportError(context, err)
    }

    fun log(msg: String) {
        println("------> $msg")
        recordedMsgs.add(LogMessage(number = recordedMsgs.size, message = msg))
        if (!MusicianshipTrainerApp.productionMode) {
            loggedMsg = msg
        }
    }
}

object MusicianshipTrainerApp {
    var productionMode = false // replace with actual production mode check
}
