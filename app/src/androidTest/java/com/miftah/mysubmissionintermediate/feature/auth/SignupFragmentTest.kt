package com.miftah.mysubmissionintermediate.feature.auth

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.miftah.mysubmissionintermediate.R
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@MediumTest
class SignupFragmentTest {

    private val actualName = "Miftah"
    private val actualEmail = "miftah@gmail.com"
    private val actualPassword = "12345678"

    @Test
    fun perform_register_success() {

        ActivityScenario.launch(WelcomeActivity::class.java)

        val onBoardScenario = launchFragmentInContainer<SignupFragment>(
            themeResId = R.style.Base_Theme_MySubmissionIntermediate
        )

        onView(withId(R.id.ed_register_name))
            .perform(ViewActions.typeText(actualName), ViewActions.closeSoftKeyboard())


//        onView(withId(R.id.signupFragment)).check(matches(isDisplayed()))
        /*onView(withId(R.id.ed_register_name))
            .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
            .perform(
                ViewActions.typeText(actualName),
                ViewActions.closeSoftKeyboard()
            )*/

        /*onView(withId(R.id.ed_register_name))
            .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
            .perform(
                ViewActions.typeText(actualName),
                ViewActions.closeSoftKeyboard()
            )*/

        /*onView(withId(R.id.ed_register_password)).perform(
            ViewActions.typeText(actualPassword),
            ViewActions.closeSoftKeyboard()
        )
        onView(withId(R.id.ed_register_email)).perform(
            ViewActions.typeText(actualEmail),
            ViewActions.closeSoftKeyboard()
        )*/
    }
}