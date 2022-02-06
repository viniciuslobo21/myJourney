package com.lobo.myjourney.domain.usecase

import com.lobo.myjourney.common.utils.Result
import com.lobo.myjourney.domain.JourneyModel
import com.lobo.myjourney.domain.model.JourneyErrorModel
import com.lobo.myjourney.domain.repository.JourneyRepository
import com.lobo.myjourney.presentation.testCore.CoroutinesTestRule
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class GetJourneyTest {

    @get: Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private val repository = mockk<JourneyRepository>()
    val getJourneysList = GetJourney(repository)

    @Test
    fun callGetJourneysList_withSuccess_shouldCallRepository() =
        coroutinesTestRule.runBlockingTest {
            prepareScenario()
            getJourneysList()
            coVerify(exactly = 1) { repository.getJourney() }
        }

    @Test
    fun callGetJourneysList_withSuccess() = coroutinesTestRule.runBlockingTest {
        val expected = Result.Success(makeJourneyList())
        prepareScenario(result = expected)
        val result = getJourneysList()
        assertEquals(expected, result)
    }

    @Test
    fun callGetJourneysList_withEmptyList() = coroutinesTestRule.runBlockingTest {
        val expected = Result.Error(JourneyErrorModel.EmptyJourney)
        prepareScenario(result = expected)
        val result = getJourneysList()
        assertEquals(expected, result)
    }

    private fun prepareScenario(
        result: Result<List<JourneyModel>, JourneyErrorModel> =
            Result.Success(makeJourneyList())
    ) {
        coEvery { repository.getJourney() } returns result
    }

    private fun makeJourneyList() = listOf(
        JourneyModel(
            title = "title",
            subtitle = "subtitle",
            day = 1,
        )
    )
}