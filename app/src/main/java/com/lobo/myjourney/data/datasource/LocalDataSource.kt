package com.lobo.myjourney.data.datasource

import com.lobo.myjourney.common.utils.Result
import com.lobo.myjourney.domain.model.JourneyModel
import com.lobo.myjourney.domain.model.JourneyErrorModel

interface LocalDataSource {
    suspend fun getJourney(): Result<List<JourneyModel>, JourneyErrorModel>
}