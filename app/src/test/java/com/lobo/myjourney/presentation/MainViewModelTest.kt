package com.lobo.myjourney.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.lobo.myjourney.common.utils.Result
import com.lobo.myjourney.common.utils.StringResourceProvider
import com.lobo.myjourney.domain.model.JourneyModel
import com.lobo.myjourney.domain.model.JourneyErrorModel
import com.lobo.myjourney.domain.usecase.GetJourneyUseCase
import com.lobo.myjourney.presentation.testCore.CoroutinesTestRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val instantTask = InstantTaskExecutorRule()

    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private val getJourneys = mockk<GetJourneyUseCase>()
    private val stringResourceProvider = mockk<StringResourceProvider>()


    private val viewModel by lazy {
        MainViewModel(
            getJourneys = getJourneys,
            stringResourceProvider = stringResourceProvider
        )
    }

    @Test
    fun init_withSuccess_shouldShowContent() = coroutinesTestRule.runBlockingTest {

        prepareScenario()

        viewModel.dispatchViewAction(MainViewActions.Init)

        coVerify {
            getJourneys()
        }
    }

    @Test
    fun init_withGenericError_shouldShowErrorContent() = coroutinesTestRule.runBlockingTest {

        val journeyResult = Result.Error(JourneyErrorModel.GenericError)

        prepareScenario(journeyResult = journeyResult)

        viewModel.dispatchViewAction(MainViewActions.Init)

        assertEquals(MainViewState.State.ERROR, viewModel.viewState.state.value)
    }

    @Test
    fun init_withEmptyList_shouldShowErrorContent() = coroutinesTestRule.runBlockingTest {

        val journeyResult = Result.Error(JourneyErrorModel.EmptyJourney)

        prepareScenario(journeyResult = journeyResult)

        viewModel.dispatchViewAction(MainViewActions.Init)

        assertEquals(MainViewState.State.ERROR, viewModel.viewState.state.value)
    }

    private fun prepareScenario(
        journeyResult: Result<List<JourneyModel>, JourneyErrorModel> =
            Result.Success(makeJourneyList())
    ) {
        coEvery { getJourneys() } returns journeyResult
        coEvery { stringResourceProvider.getString(any(), any()) } returns ""
    }

    private fun makeJourneyList() = listOf(
        JourneyModel(
            title = "title",
            subtitle = "subtitle",
            day = 1,
        )
    )
}