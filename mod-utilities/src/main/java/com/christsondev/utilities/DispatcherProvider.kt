package com.christsondev.utilities

import kotlinx.coroutines.Dispatchers

object DispatcherProvider {
    fun main() = Dispatchers.Main
    fun io() = Dispatchers.IO
}