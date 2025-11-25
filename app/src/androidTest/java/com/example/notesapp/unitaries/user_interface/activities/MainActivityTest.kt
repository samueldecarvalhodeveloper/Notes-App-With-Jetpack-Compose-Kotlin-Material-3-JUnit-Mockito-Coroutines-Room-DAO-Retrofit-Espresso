package com.example.notesapp.unitaries.user_interface.activities

import android.app.Instrumentation
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By.text
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until.hasObject
import com.example.notesapp.R
import com.example.notesapp.constants.user_interface.UserInterfaceConstants.CREATE_USER_BUTTON_TEXT
import com.example.notesapp.constants.user_interface.UserInterfaceConstants.LAUNCH_TIMEOUT
import junit.framework.TestCase.assertNotNull
import org.junit.AfterClass
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Before
    fun beforeEach() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        device.pressHome()

        val appName = context.getString(R.string.app_name)
        val appIconElement = device.findObject(text(appName))

        appIconElement.click()

        device.wait(hasObject(text(CREATE_USER_BUTTON_TEXT)), LAUNCH_TIMEOUT)
    }

    @Test
    fun testIfUserInterfaceAndRoutesAreSetAndUserExistingIsVerified() {
        val createUserButton = device.findObject(text(CREATE_USER_BUTTON_TEXT))

        assertNotNull(createUserButton)
    }

    companion object {
        private lateinit var instrumentation: Instrumentation
        private lateinit var device: UiDevice

        @BeforeClass
        @JvmStatic
        fun beforeAll() {
            instrumentation = InstrumentationRegistry.getInstrumentation()
            device = UiDevice.getInstance(instrumentation)
        }

        @AfterClass
        @JvmStatic
        fun afterAll() {
            device.pressHome()
        }
    }
}
