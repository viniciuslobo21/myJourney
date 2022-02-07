package com.lobo.myjourney.domain.usecase

import com.lobo.myjourney.common.utils.Result
import com.lobo.myjourney.domain.model.JourneyModel
import com.lobo.myjourney.domain.model.JourneyErrorModel
import com.lobo.myjourney.domain.repository.JourneyRepository

class GetJourney(
    private val journeyRepository: JourneyRepository
) : GetJourneyUseCase {

    override suspend fun invoke(): Result<List<JourneyModel>, JourneyErrorModel> {
        return journeyRepository.getJourney()
    }
}