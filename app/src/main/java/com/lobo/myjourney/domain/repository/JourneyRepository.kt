package com.lobo.myjourney.domain.repository

import com.lobo.myjourney.common.utils.Result
import com.lobo.myjourney.domain.model.JourneyModel
import com.lobo.myjourney.domain.model.JourneyErrorModel

interface JourneyRepository {
    suspend fun getJourney(): Result<List<JourneyModel>, JourneyErrorModel>
}