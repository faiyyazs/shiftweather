package com.shiftweather.presentation.forecast

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.shiftweather.R
import com.shiftweather.core.presentation.*
import com.shiftweather.core.presentation.ui.BaseActivity
import com.shiftweather.databinding.ActivityWeatherforecastBinding
import com.shiftweather.presentation.city.CityForecastActivity
import com.shiftweather.presentation.forecast.adapter.DatesAdapter
import com.shiftweather.presentation.model.City
import com.shiftweather.presentation.model.WeatherForecastPresentation
import com.shiftweather.presentation.model.WeatherTab
import com.shiftweather.presentation.settings.SettingsActivity
import com.shiftweather.presentation.shared.SharedViewModel
import com.shiftweather.presentation.today.weather.WeatherFragment
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.atomic.AtomicBoolean

class WeatherForecastActivity :
    BaseActivity<ActivityWeatherforecastBinding, WeatherForecastViewModel>(),ViewPager.OnPageChangeListener {

    /**
     *
     * Used Koin for Dependency Injection
     *
     * */
    private val activityModel: WeatherForecastViewModel by viewModel()
    private val sharedViewModel: SharedViewModel by viewModel()
    private val datesAdapter by inject<DatesAdapter>()

    /**
     *
     * Loading of fragment by lazy to be loaded when used further.
     * */
    private val weatherDayFragment: WeatherFragment by lazy {  WeatherFragment() }
    private val weatherNightFragment: WeatherFragment by lazy { WeatherFragment() }


    override fun getLayoutId(): Int {
        return R.layout.activity_weatherforecast
    }

    override fun getViewModel(): WeatherForecastViewModel {
        return activityModel
    }

    /**
     * Opens places around me to view city forecast for current day.
     *
     * */
    private fun openCities(cities: List<City>): Boolean {
        startActivity(CityForecastActivity.newIntent(this,cities))
        return true
    }


    override fun setUp(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            activityModel.getForecastData()
        }

        getViewModel().weatherForecast.observe(this, Observer {

            it?.let { resource ->
                when (resource.state) {
                    ResourceState.LOADING -> {
                        showLoading()
                    }
                    ResourceState.ERROR -> {
                        showError()
                    }
                    ResourceState.SUCCESS -> {
                        loadContent(it)
                    }
                }
            }
        })

        getViewDataBinding().pullToRefresh.setOnRefreshListener {
            activityModel.getForecastData()
        }

        sharedViewModel.fragmentReloadData.observe(this, Observer {
           refresh->
            if(refresh){
                activityModel.getForecastData()
            }
        })

        getViewModel().tabChangeListener.observe(this, Observer {
            weatherTab->
            updateDataOnTabChange(weatherTab)
        })


        getViewDataBinding().tabs.addOnTabSelectedListener(
            object: TabLayout.OnTabSelectedListener{
                private var alreadyReselected = AtomicBoolean(false)


                override fun onTabReselected(tab: TabLayout.Tab?) {
                    if  (tab?.position == 0 && !alreadyReselected.getAndSet(true)) onTabSelected(tab)
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {}

                override fun onTabSelected(tab: TabLayout.Tab?) {
                    tab?.let {
                        getViewModel().filterData(it.position)
                    }

                }

            }

        )

        getViewModel().tabChangeListener.value?.let {
            getViewDataBinding().tabs.getTabAt(it.position)?.select()
        }

        getViewDataBinding().toolbar.menu.findItem(R.id.menu_settings).setOnMenuItemClickListener {
            startActivity(SettingsActivity.newIntent(this@WeatherForecastActivity))
            return@setOnMenuItemClickListener true
        }


    }

    private fun updateDataOnTabChange(weatherTab: WeatherTab?) {
        if(weatherTab?.position == WeatherTab.DAY.position){
            openFragment(R.id.fragmentContainer,weatherDayFragment)
        }else{
            openFragment(R.id.fragmentContainer,weatherNightFragment)
        }
        getViewModel().weatherForecast.value?.let {
                presentationData->
            presentationData.data?.let {
                weatherTab?.let {
                    updateWeatherFragment(presentationData.data)
                }
            }
        }
    }

    private fun loadContent(resource: Resource<WeatherForecastPresentation>) {
        getViewDataBinding().pullToRefresh.stopRefreshing()
        resource.data?.let {
                presentationData ->

            getViewDataBinding().viewpagerDateSelector.adapter = datesAdapter
            datesAdapter.setDates(resource.data.dates)

            if(datesAdapter.getDates().isNotEmpty()){
                getViewDataBinding().datesHolder.visible()
            }else{
                getViewDataBinding().datesHolder.invisible()
            }

            getViewModel().dateChangeListener.observe(this, Observer {

                updateWeatherFragment(presentationData)

                presentationData.cities?.let {
                        cities->

                    val cityData =  cities.firstOrNull {
                            city -> city.date ==  it.date
                    }
                    cityData?.let {
                        cityData.cities?.let {
                                cityList ->
                            showCitiesMenu()
                            getViewDataBinding().toolbar.menu.findItem(R.id.menu_cities).setOnMenuItemClickListener {
                                openCities(cityList)
                            }
                        }?: run {
                            hideCitiesMenu()
                        }
                    }?:run {
                        hideCitiesMenu()
                    }
                }?: run {
                    hideCitiesMenu()
                }
            })

            getViewDataBinding().swipeLeft.setOnClickListener {
                getViewDataBinding().viewpagerDateSelector.arrowScroll(View.FOCUS_LEFT) }
            getViewDataBinding().swipeRight.setOnClickListener {
                getViewDataBinding().viewpagerDateSelector.arrowScroll(View.FOCUS_RIGHT) }
            getViewDataBinding().viewpagerDateSelector.addOnPageChangeListener(this)

            getViewModel().dateChangeListener.value?.let {
                    weatherDate->
                val indexOf = datesAdapter.getDates().indexOf(weatherDate)
                getViewDataBinding().viewpagerDateSelector.currentItem = indexOf

            }?: run {
                onPageSelected(0)
            }
        }
    }

    private fun showCitiesMenu() {
        getViewDataBinding().toolbar.menu.findItem(R.id.menu_cities).isVisible = true
        getViewDataBinding().toolbar.menu.findItem(R.id.menu_cities).isEnabled = true
    }

    private fun hideCitiesMenu() {
        getViewDataBinding().toolbar.menu.findItem(R.id.menu_cities).isVisible = false
        getViewDataBinding().toolbar.menu.findItem(R.id.menu_cities).isEnabled = false
    }


    private fun updateWeatherFragment(presentationData: WeatherForecastPresentation) {
        sharedViewModel._fragmentPresentationData.success( getViewModel().loadData(presentationData,getViewModel().tabChangeListener.value))
    }

    private fun showError() {
        getViewDataBinding().pullToRefresh.stopRefreshing()
        sharedViewModel._fragmentPresentationData.error()
    }

    private fun showLoading() {
        getViewDataBinding().toolbar.menu.findItem(R.id.menu_cities).isEnabled = false
        getViewDataBinding().pullToRefresh.startRefreshing()
        sharedViewModel._fragmentPresentationData.loading()
    }

    override fun onPageScrollStateChanged(state: Int) {

    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        getViewDataBinding().swipeLeft.isEnabled = position != 0
        getViewDataBinding().swipeRight.isEnabled = position != datesAdapter.count - 1
    }

    override fun onPageSelected(position: Int) {
        getViewModel().dateChanged(datesAdapter.getDates()[position])
    }



}