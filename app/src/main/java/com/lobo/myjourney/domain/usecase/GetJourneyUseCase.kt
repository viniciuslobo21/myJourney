package com.lobo.myjourney.domain.usecase

import com.lobo.myjourney.common.utils.Result
import com.lobo.myjourney.domain.JourneyModel
import com.lobo.myjourney.domain.model.JourneyErrorModel

interface GetJourneyUseCase {
    suspend operator fun invoke(): Result<List<JourneyModel>, JourneyErrorModel>
}