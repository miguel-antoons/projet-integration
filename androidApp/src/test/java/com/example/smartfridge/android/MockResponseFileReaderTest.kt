package com.example.smartfridge.android

import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MockResponseFileReaderTest {
    @Test
    fun `read a file should not create any errors`() {
        val reader = MockResponseFileReader("test.json")
        assertEquals(reader.content, "success")
    }
}