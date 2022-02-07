package com.lobo.myjourney.data.repository

import com.lobo.myjourney.common.utils.Result
import com.lobo.myjourney.data.datasource.LocalDataSource
import com.lobo.myjourney.domain.model.JourneyModel
import com.lobo.myjourney.domain.model.JourneyErrorModel
import com.lobo.myjourney.domain.repository.JourneyRepository

class JourneyDefaultRepository(
    private val localDataSource: LocalDataSource
) : JourneyRepository {

    override suspend fun getJourney(): Result<List<JourneyModel>, JourneyErrorModel> {
        return localDataSource.getJourney()
    }
}