package com.darja.tmdb

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.darja.tmdb.api.model.MoviesPage
import com.darja.tmdb.repo.MoviesRepo
import com.darja.tmdb.util.fromJsonFile
import com.google.gson.Gson
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.dsl.module.module
import org.koin.standalone.StandAloneContext.loadKoinModules
import org.koin.test.AutoCloseKoinTest
import org.koin.test.declareMock

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class MoviesListTest: AutoCloseKoinTest() {
//    val repo: MoviesRepo by inject()

    private val testModule = module {
        single(override = true) { MockRepo() as MoviesRepo }
    }


    @Rule
    @JvmField
    val rule = ActivityTestRule(MainActivity::class.java, true, true)

    @Before
    fun before() {
        loadKoinModules(testModule)
//        stopKoin()
//        loadKoinModules(testModule, viewModels, app)
        declareMock<MoviesRepo>()
//            given(repo.getNowPlayingMovies()).will { readList() }
//        }
    }

    @Test
    fun loadContent() {
//        given(repo.getNowPlayingMovies()).will { readList() }

        onView(withId(R.id.progressBar)).check(getViewAssertion(ViewMatchers.Visibility.VISIBLE))
        Thread.sleep(5000)
        onView(withId(R.id.progressBar)).check(getViewAssertion(ViewMatchers.Visibility.GONE))
    }

    private fun readList(): Single<MoviesPage> {
        return Gson().fromJsonFile(InstrumentationRegistry.getInstrumentation().context, "list.json")
    }

    private fun getViewAssertion(visibility: ViewMatchers.Visibility): ViewAssertion? {
        return ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(visibility))
    }

    class MockRepo: MoviesRepo {
        override fun getNowPlayingMovies(): Single<MoviesPage> {
            return Gson().fromJsonFile(InstrumentationRegistry.getInstrumentation().context, "list.json")
        }

        override fun searchMovies(query: String): Single<MoviesPage> {
            return Gson().fromJsonFile(InstrumentationRegistry.getInstrumentation().context, "list.json")
        }

    }
}
