package com.example.smartfridge.android

import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.launchActivity
import androidx.test.ext.junit.runners.AndroidJUnit4
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FormsAddAlimentsTest {
    private lateinit var scenario: ActivityScenario<FormsAddAliments>
    private lateinit var server: MockWebServer

    @Before
    fun setUp() {
        scenario = launchActivity()
        // server = MockWebServer()
    }

//    @After
//    fun shutdown() {
//        server.shutdown()
//    }

    @Test
    fun `Activity should change state to create without error`() {
        scenario.moveToState(Lifecycle.State.CREATED)
    }

    @Test
    fun `Activity should change state to started without errors`() {
        scenario.moveToState(Lifecycle.State.STARTED)
    }

    @Test
    fun `Activity should change state to resumed without errors`() {
        scenario.moveToState(Lifecycle.State.RESUMED)
    }

    @Test
    fun `Activity should change state to destroyed without errors`() {
        scenario.moveToState(Lifecycle.State.DESTROYED)
    }

    @Test
    fun `Activity should recreate without errors`() {
        scenario.recreate()
    }
}