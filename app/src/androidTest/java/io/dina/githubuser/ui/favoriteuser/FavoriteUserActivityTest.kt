package io.dina.githubuser.ui.favoriteuser

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import io.dina.githubuser.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class FavoriteUserActivityTest {

    @Before
    fun setup() {
        ActivityScenario.launch(FavoriteUserActivity::class.java)
    }

    @Test
    fun getList() {
        onView(withId(R.id.rv_users)).check(matches(isDisplayed()))
    }
}