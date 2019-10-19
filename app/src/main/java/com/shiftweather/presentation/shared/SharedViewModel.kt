package com.shiftweather.presentation.shared

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.shiftweather.core.presentation.Resource
import com.shiftweather.core.presentation.ui.BaseViewModel
import com.shiftweather.presentation.model.WeatherData


/**
 * Model used to share data between the activity & fragments
 *
 * */
open class SharedViewModel(application: Application) : BaseViewModel(application) {

    val fragmentPresentationData = MutableLiveData<Resource<FragmentPresentationModel>>()
    val fragmentReloadData = MutableLiveData<Boolean>()

}

data class FragmentPresentationModel(
    val now: WeatherData?,
    val tom: List<WeatherData>?
)

