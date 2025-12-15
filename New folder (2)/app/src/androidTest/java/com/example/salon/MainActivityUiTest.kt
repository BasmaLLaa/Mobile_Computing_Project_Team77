package com.example.salon

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * UI tests for MainActivity using AndroidJUnit4 + Compose UI Test.
 */
@RunWith(AndroidJUnit4::class)
class MainActivityUiTest {

    // Launches MainActivity before each test
    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun bookAppointment_navigatesToServiceListScreen() {
        // Home screen: Book button visible
        composeRule.onNodeWithText("Book Appointment")
            .assertIsDisplayed()

        // Click the button
        composeRule.onNodeWithText("Book Appointment")
            .performClick()

        // Now the service list screen title is visible
        composeRule.onNodeWithText("Choose a Service")
            .assertIsDisplayed()
    }

    @Test
    fun myBookings_navigatesToBookingListScreen() {
        // Home screen: My Bookings button visible
        composeRule.onNodeWithText("My Bookings")
            .assertIsDisplayed()

        // Click the button
        composeRule.onNodeWithText("My Bookings")
            .performClick()

        // Booking list screen shows its title
        composeRule.onNodeWithText("My Bookings")
            .assertIsDisplayed()
    }
}
