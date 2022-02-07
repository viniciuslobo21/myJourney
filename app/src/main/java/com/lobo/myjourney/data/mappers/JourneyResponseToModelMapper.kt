package com.lobo.myjourney.data.mappers

import com.lobo.myjourney.common.base.BaseMapper
import com.lobo.myjourney.data.model.JourneyResponse
import com.lobo.myjourney.domain.model.JourneyModel


class JourneyResponseToModelMapper : BaseMapper<JourneyResponse, JourneyModel> {

    override fun mapFromDomain(domain: JourneyModel): JourneyResponse {
        throw UnsupportedOperationException("Unsupported Operation")
    }

    override fun mapToDomain(
        model: JourneyResponse
    ): JourneyModel =
        JourneyModel(title = model.title, subtitle = model.subtitle, day = model.day)

    fun mapFromDomainList(list: List<JourneyResponse>?) = list?.map {
        mapToDomain(it)
    }.orEmpty()
}