package com.example.mediaplayer.util

sealed class Status
data class Succeed(val message: String) : Status()

data class Failed(val message: String, val reason: FailingReason) : Status()
data class Loading(var message: String = "") : Status()
