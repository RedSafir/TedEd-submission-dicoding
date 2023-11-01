package com.miftah.mysubmissionintermediate.feature.auth

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.miftah.mysubmissionintermediate.R
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginFragmentTest {

    @Test
    fun perform_login_success() {
        launchFragmentInContainer<SignupFragment>(themeResId = R.style.Base_Theme_MySubmissionIntermediate)
        Espresso.onView(ViewMatchers.withId(R.id.ed_login_email))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}