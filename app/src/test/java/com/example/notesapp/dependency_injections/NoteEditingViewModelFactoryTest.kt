package com.example.notesapp.dependency_injections

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import junit.framework.TestCase.assertFalse
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class NoteEditingViewModelFactoryTest {
    @Test
    fun testIfMethodGetInstanceReturnsAWorkingInstance() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        val noteEditingViewModel = NoteEditingViewModelFactory.getInstance(context)

        assertFalse(noteEditingViewModel.noteState.isInitialized)
    }
}