package com.lobo.myjourney.data.datasource

import android.content.Context
import com.lobo.myjourney.common.utils.Converters.getJson
import com.lobo.myjourney.common.utils.Result
import com.lobo.myjourney.data.mappers.JourneyResponseToModelMapper
import com.lobo.myjourney.data.model.JourneyResponse
import com.lobo.myjourney.domain.model.JourneyModel
import com.lobo.myjourney.domain.model.JourneyErrorModel
import kotlinx.coroutines.delay

class LocalDataSourceImpl(
    private val context: Context
) : LocalDataSource {
    override suspend fun getJourney(): Result<List<JourneyModel>, JourneyErrorModel> {
        delay(1500L)
        val json: List<JourneyResponse> = getJson(context, "data.json")

        return if (json.isNotEmpty())
            Result.Success(JourneyResponseToModelMapper().mapFromDomainList(json))
        else {
            Result.Error(JourneyErrorModel.EmptyJourney)
        }
    }
}