package com.stebitto.agify

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.stebitto.agify.impl.ui.AgifyFragment
import com.stebitto.agify.impl.ui.AgifyViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@HiltAndroidTest
class AgifyFragmentTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun test_Init_state() {
        launchFragmentInHiltContainer<AgifyFragment> {
            renderUI(
                AgifyViewModel.State.Init
            )
        }

        onView(withId(R.id.button_submit)).check(matches(isClickable()))
    }

    @Test
    fun test_Loading_state() {
        launchFragmentInHiltContainer<AgifyFragment> {
            renderUI(
                AgifyViewModel.State.Loading
            )
        }

        onView(withId(R.id.button_submit)).check(matches(isNotClickable()))
    }

    @Test
    fun test_Success_state() {
        launchFragmentInHiltContainer<AgifyFragment> {
            renderUI(
                AgifyViewModel.State.Success("", 0)
            )
        }

        val context = ApplicationProvider.getApplicationContext<Context>()
        val stringToTest = String.format(context.getString(R.string.agify_predict_result), "", 0)

        onView(withId(R.id.button_submit)).check(matches(isClickable()))
        onView(withId(R.id.textview_result)).check(matches(withText(stringToTest)))
    }

    @Test
    fun test_Error_state() {
        launchFragmentInHiltContainer<AgifyFragment> {
            renderUI(
                AgifyViewModel.State.Error(0, "error")
            )
        }

        val context = ApplicationProvider.getApplicationContext<Context>()
        val stringToTest = String.format(context.getString(R.string.agify_predict_error), 0, "error")

        onView(withId(R.id.button_submit)).check(matches(isClickable()))
        onView(withId(R.id.textview_result)).check(matches(withText(stringToTest)))
    }
}