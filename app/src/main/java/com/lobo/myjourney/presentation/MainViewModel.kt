package com.lobo.myjourney.presentation

import androidx.lifecycle.viewModelScope
import com.lobo.myjourney.common.base.BaseViewModel
import com.lobo.myjourney.common.utils.StringResourceProvider
import com.lobo.myjourney.domain.usecase.GetJourneyUseCase
import com.lobo.myjourney.presentation.mappers.JourneyModelToJourneyUiModelMapper
import kotlinx.coroutines.launch

class MainViewModel(
    private val getJourneys: GetJourneyUseCase,
    private val stringResourceProvider: StringResourceProvider
) : BaseViewModel<MainViewState, MainViewActions>() {

    override val viewState = MainViewState()

    override fun dispatchViewAction(viewAction: MainViewActions) {
        when (viewAction) {
            is MainViewActions.Init -> init()
        }
    }

    private fun init() {
        viewModelScope.launch {
            viewState.state.postValue(MainViewState.State.LOADING)

            getJourneys().handleResult(
                onSuccess = {
                    viewState.journeysList.value = JourneyModelToJourneyUiModelMapper(
                        stringResourceProvider
                    ).mapFromDomainList(it)
                    viewState.state.postValue(MainViewState.State.SUCCESS)
                }, onError = {
                    viewState.state.postValue(MainViewState.State.ERROR)
                }
            )
        }
    }
}