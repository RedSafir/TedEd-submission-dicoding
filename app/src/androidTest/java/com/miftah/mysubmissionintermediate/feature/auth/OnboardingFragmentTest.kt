package com.miftah.mysubmissionintermediate.feature.auth

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.filters.MediumTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.miftah.mysubmissionintermediate.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
@MediumTest
class OnboardingFragmentTest {

    @Before
    fun setup() {
        ActivityScenario.launch(WelcomeActivity::class.java)
    }

    @Test
    fun loadOnboard_to_login() {
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )
        val onBoardScenario = launchFragmentInContainer<OnboardingFragment>()
        onBoardScenario.onFragment { fragment ->
            navController.setGraph(R.navigation.mobile_navigation)
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        onView(withId(R.id.btn_login)).perform(click())
        onView(withText("Login")).check(matches(isDisplayed()))
    }

    @Test
    fun loadOnboard_to_signup() {
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )
        val onBoardScenario = launchFragmentInContainer<OnboardingFragment>()
        onBoardScenario.onFragment { fragment ->
            navController.setGraph(R.navigation.mobile_navigation)
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        onView(withId(R.id.btn_signup)).perform(click())
        onView(withId(R.id.signup_fragment)).check(matches(isDisplayed()))
    }
}