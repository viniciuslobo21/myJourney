package com.lobo.myjourney.presentation.mappers

import com.lobo.myjourney.R
import com.lobo.myjourney.common.base.BaseMapper
import com.lobo.myjourney.common.utils.NumbersToDescribedNumbers.convertNumberToWord
import com.lobo.myjourney.common.utils.StringResourceProvider
import com.lobo.myjourney.domain.model.JourneyModel
import com.lobo.myjourney.presentation.model.JourneyUiModel


class JourneyModelToJourneyUiModelMapper(
    private val stringResourceProvider: StringResourceProvider
) : BaseMapper<JourneyUiModel, JourneyModel> {

    override fun mapFromDomain(domain: JourneyModel) =
        JourneyUiModel(
            title = domain.title,
            subtitle = domain.subtitle,
            day = domain.day,
            dayInFull = stringResourceProvider.getString(
                R.string.day, domain.day.convertNumberToWord()
            )
        )

    override fun mapToDomain(model: JourneyUiModel): JourneyModel {
        throw UnsupportedOperationException("Unsupported Operation")
    }

    fun mapFromDomainList(list: List<JourneyModel>?) = list?.map {
        mapFromDomain(it)
    }.orEmpty()
}