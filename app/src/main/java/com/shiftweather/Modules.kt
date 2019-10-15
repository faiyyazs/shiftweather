package com.shiftweather

import com.shiftweather.data.datasource.IForecastRemoteDataSource
import com.shiftweather.data.repository.ForecastRepositoryImpl
import com.shiftweather.datasource.remote.ForecastRemoteDataSourceImpl
import com.shiftweather.datasource.remote.forecast.ForecastRequest
import com.shiftweather.domain.repository.IForecastRepository
import com.shiftweather.domain.usecase.ForecastUseCase
import com.shiftweather.presentation.city.CityViewModel
import com.shiftweather.presentation.forecast.WeatherForecastViewModel
import com.shiftweather.presentation.forecast.adapter.DatesAdapter
import com.shiftweather.presentation.localization.LocaleActivityImpl
import com.shiftweather.presentation.onboarding.SplashViewModel
import com.shiftweather.presentation.settings.SettingsViewModel
import com.shiftweather.presentation.shared.SharedViewModel
import com.shiftweather.presentation.today.adapter.ForecastAdapter
import com.shiftweather.presentation.today.adapter.WindsAdapter
import com.shiftweather.presentation.today.weather.WeatherViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.loadKoinModules
import org.koin.core.module.Module
import org.koin.dsl.module


fun injectFeature() = loadFeature

private val loadFeature by lazy {

    loadKoinModules(
        listOf(
            viewModelModule,
            useCaseModule,
            repositoryModule,
            dataSourceModule,
            networkModule,
            adaptersModule,
            supportModule
        )
    )
}

private val viewModelModule: Module = module {
    viewModel { SplashViewModel(application = get()) }
    viewModel { WeatherForecastViewModel(application = get(),forecastUseCase = get()) }
    viewModel { SharedViewModel(application = get()) }
    viewModel { CityViewModel(application = get()) }
    viewModel { WeatherViewModel(application = get()) }
    viewModel { SettingsViewModel(application = get()) }

}

private val useCaseModule: Module = module {
    factory { ForecastUseCase(forecastRepository = get()) }
}

private val repositoryModule: Module = module {
    single { ForecastRepositoryImpl(remoteDataSource = get()) as IForecastRepository }
}

private val dataSourceModule: Module = module {
    single { ForecastRemoteDataSourceImpl(request = forecastsRequest) as IForecastRemoteDataSource }
}

private val networkModule: Module = module {
    single { forecastsRequest }
}

private val supportModule: Module = module {
    factory { LocaleActivityImpl()}
}

private val adaptersModule: Module = module {
    single { DatesAdapter() }
    single { ForecastAdapter() }
    single { WindsAdapter() }
}

private val forecastsRequest: ForecastRequest = ForecastRequest()


