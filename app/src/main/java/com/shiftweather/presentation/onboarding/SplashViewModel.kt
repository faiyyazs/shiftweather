package com.shiftweather.presentation.onboarding

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.shiftweather.core.presentation.ui.BaseViewModel
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class SplashViewModel(application: Application) : BaseViewModel(application){

    val navigationObservable = MutableLiveData<Boolean>()


    fun startMainActivity(){
        addCompositeDisposable(Observable.timer(3, TimeUnit.SECONDS)
            .subscribe {
                navigationObservable.postValue(true)
            })
    }

}