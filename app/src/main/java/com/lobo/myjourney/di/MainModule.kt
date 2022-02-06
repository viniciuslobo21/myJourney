package com.lobo.myjourney.di

import com.lobo.myjourney.common.utils.ContextStringResourceProvider
import com.lobo.myjourney.common.utils.StringResourceProvider
import com.lobo.myjourney.data.datasource.LocalDataSource
import com.lobo.myjourney.data.datasource.LocalDataSourceImpl
import com.lobo.myjourney.data.repository.JourneyDefaultRepository
import com.lobo.myjourney.domain.repository.JourneyRepository
import com.lobo.myjourney.domain.usecase.GetJourney
import com.lobo.myjourney.domain.usecase.GetJourneyUseCase
import com.lobo.myjourney.presentation.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val repositoryModule = module {
    single<JourneyRepository> {
        JourneyDefaultRepository(
            localDataSource = get()
        )
    }
}

val dataSourceModule = module {
    single<LocalDataSource> {
        LocalDataSourceImpl(
            context = androidContext()
        )
    }
}

val useCaseModule = module {
    factory<GetJourneyUseCase> {
        GetJourney(
            journeyRepository = get()
        )
    }
}

val presentationModule = module {
    viewModel { MainViewModel(
        stringResourceProvider = get(),
        getJourneys = get()
    ) }
}

val resourceProvider = module {
    factory<StringResourceProvider> {
        ContextStringResourceProvider(
            context = androidContext()
        )
    }
}