package com.lobo.myjourney.data.repository

import com.lobo.myjourney.common.utils.Result
import com.lobo.myjourney.data.datasource.LocalDataSource
import com.lobo.myjourney.domain.JourneyModel
import com.lobo.myjourney.domain.model.JourneyErrorModel
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
class JourneyDefaultRepositoryTest {

    @get: Rule
    val coroutinesTestRule = CoroutinesTestRule()

    private val localDataSource = mockk<LocalDataSource>()
    private val repository = JourneyDefaultRepository(
        localDataSource = localDataSource
    )

    @Test
    fun callDatasource_withSuccess() = coroutinesTestRule.runBlockingTest {
        prepareScenario()
        repository.getJourney()
        coVerify(exactly = 1) {
            localDataSource.getJourney()
        }
    }

    @Test
    fun callDatasource_returning_emptyList() = coroutinesTestRule.runBlockingTest {
        val expected = Result.Error(JourneyErrorModel.EmptyJourney)
        prepareScenario(result = expected)
        val result = repository.getJourney()
        assertEquals(expected, result)
    }

    @Test
    fun callDatasource_returning_genericError() = coroutinesTestRule.runBlockingTest {
        val expected = Result.Error(JourneyErrorModel.GenericError)
        prepareScenario(result = expected)
        val result = repository.getJourney()
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