package com.lobo.myjourney.domain.model


sealed class JourneyErrorModel(val message: String?) {
    object EmptyJourney : JourneyErrorModel(message = null)
    object GenericError : JourneyErrorModel(message = null)
}