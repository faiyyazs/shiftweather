package com.shiftweather.presentation.onboarding

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.shiftweather.R
import com.shiftweather.core.presentation.ui.BaseActivity
import com.shiftweather.databinding.ActivitySplashBinding
import com.shiftweather.presentation.forecast.WeatherForecastActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>() {


    private val splashViewModel: SplashViewModel by viewModel()


    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun getViewModel(): SplashViewModel {
        return splashViewModel
    }

    override fun setUp(savedInstanceState: Bundle?) {

        if (savedInstanceState == null) {
            splashViewModel.startMainActivity()
        }

        splashViewModel.navigationObservable.observe(this, Observer {
            if (it) {
                val intent = Intent(this@SplashActivity, WeatherForecastActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        })
    }


}