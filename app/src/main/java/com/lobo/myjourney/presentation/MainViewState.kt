package com.lobo.myjourney.presentation

import androidx.lifecycle.MutableLiveData
import com.lobo.myjourney.presentation.model.JourneyUiModel

class MainViewState {
    enum class State { LOADING, ERROR, SUCCESS }

    val state = MutableLiveData<State>()

    val journeysList = MutableLiveData<List<JourneyUiModel>>()
}