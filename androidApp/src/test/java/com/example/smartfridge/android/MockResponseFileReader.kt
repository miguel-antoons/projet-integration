package com.example.smartfridge.android

import java.io.InputStreamReader


// class reads a resources file and stores the content of that file in its content variable
class MockResponseFileReader(path: String) {
    val content: String

    init {
        val reader = InputStreamReader(this.javaClass.classLoader?.getResourceAsStream(path))
        content = reader.readText()
        reader.close()
    }
}