package com.shiftweather.presentation.today.weather


import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.shiftweather.R
import com.shiftweather.core.presentation.Resource
import com.shiftweather.core.presentation.ResourceState
import com.shiftweather.core.presentation.gone
import com.shiftweather.core.presentation.ui.BaseFragment
import com.shiftweather.core.presentation.visible
import com.shiftweather.databinding.FragmentWeatherBinding
import com.shiftweather.presentation.shared.FragmentPresentationModel
import com.shiftweather.presentation.shared.SharedViewModel
import com.shiftweather.presentation.today.SpreadEvenLinearLayoutManager
import com.shiftweather.presentation.today.adapter.ForecastAdapter
import com.shiftweather.presentation.today.adapter.WindsAdapter
import com.shiftweather.presentation.utils.getDegreesRepresentation
import com.shiftweather.presentation.utils.icon
import kotlinx.android.synthetic.main.layout_error.view.*
import kotlinx.android.synthetic.main.layout_now.view.*
import kotlinx.android.synthetic.main.layout_sea_peapsi.view.*
import kotlinx.android.synthetic.main.layout_tomorrow.view.*
import kotlinx.android.synthetic.main.layout_weatherdata.view.*
import kotlinx.android.synthetic.main.layout_winds.view.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class WeatherFragment : BaseFragment<FragmentWeatherBinding, WeatherViewModel>() {


    private val fragmentViewModel: WeatherViewModel by viewModel()
    private val sharedViewModel: SharedViewModel by sharedViewModel()

    private val adapter by inject<ForecastAdapter>()
    private val windsadapter by inject<WindsAdapter>()

    override fun getLayoutId(): Int {
        return R.layout.fragment_weather
    }

    override fun getViewModel(): WeatherViewModel {
        return fragmentViewModel
    }

    override fun setUp(view: View, savedInstanceState: Bundle?) {


        sharedViewModel.fragmentPresentationData.value?.let {
            refreshContent(it, view)
        } ?: run {
            showError()
        }

        sharedViewModel.fragmentPresentationData.observe(this, Observer {
            refreshContent(it, view)
        })


    }

    private fun refreshContent(it: Resource<FragmentPresentationModel>, view: View) {
        when (it.state) {

            ResourceState.LOADING -> showloadingState()
            ResourceState.ERROR -> showError()
            ResourceState.SUCCESS -> showData()

        }
        it.data?.let {
            loadContent(view, it)
        }
        it.message?.let {
        }

    }

    private fun loadContent(view: View, fragmentPresentationModel: FragmentPresentationModel) {

        fragmentPresentationModel.let {

            fragmentPresentationModel.now?.let { nowData ->

                getViewDataBinding().weatherData.now.phenomenon.text =
                    nowData.weather.phenomenon.phenomenon
                getViewDataBinding().weatherData.now.tempMin.text = nowData.weather.tempmin?.let {
                    getDegreesRepresentation(
                        view.context,
                        it
                    )
                }
                getViewDataBinding().weatherData.now.tempMax.text = nowData.weather.tempmax?.let {
                    getDegreesRepresentation(
                        view.context,
                        it
                    )
                }

                getViewDataBinding().weatherData.now.weather_type.setImageResource(nowData.weather.phenomenon.icon())
                getViewDataBinding().weatherData.description.text = nowData.text

                nowData.sea?.let {
                    if (it.isEmpty()) {
                        getViewDataBinding().weatherData.moredetails.weatherAtSeaAndPeipsi.seaContainer.gone()
                    } else {
                        getViewDataBinding().weatherData.moredetails.weatherAtSeaAndPeipsi.seaContainer.visible()
                    }

                    getViewDataBinding().weatherData.moredetails.weatherAtSeaAndPeipsi.seaLevel.text =
                        nowData.sea
                } ?: run {
                    getViewDataBinding().weatherData.moredetails.weatherAtSeaAndPeipsi.seaContainer.gone()
                }

                nowData.peipsi?.let {
                    if (it.isEmpty()) {
                        getViewDataBinding().weatherData.moredetails.weatherAtSeaAndPeipsi.peipsiContainer.gone()
                    } else {
                        getViewDataBinding().weatherData.moredetails.weatherAtSeaAndPeipsi.peipsiContainer.visible()
                    }
                    getViewDataBinding().weatherData.moredetails.weatherAtSeaAndPeipsi.peipsiLevel.text =
                        nowData.peipsi

                }
                    ?: run { getViewDataBinding().weatherData.moredetails.weatherAtSeaAndPeipsi.peipsiContainer.gone() }


                getViewDataBinding().weatherData.winds.recyclerViewWinds.adapter = windsadapter
                val spanningLinearLayoutManager = SpreadEvenLinearLayoutManager(view.context)
                spanningLinearLayoutManager.orientation = RecyclerView.HORIZONTAL
                getViewDataBinding().weatherData.winds.recyclerViewWinds.layoutManager =
                    spanningLinearLayoutManager

                nowData.wind?.let {
                    if (it.isEmpty()) {
                        getViewDataBinding().weatherData.winds.gone()
                    } else {
                        getViewDataBinding().weatherData.winds.visible()
                    }
                    windsadapter.submitList(it)
                } ?: run {
                    getViewDataBinding().weatherData.winds.gone()
                }

                fragmentPresentationModel.tom?.let { tomData ->
                    getViewDataBinding().weatherData.recyclerViewTom.adapter = adapter
                    val spanningLinearLayoutManager = SpreadEvenLinearLayoutManager(view.context)
                    spanningLinearLayoutManager.orientation = RecyclerView.HORIZONTAL
                    getViewDataBinding().weatherData.recyclerViewTom.layoutManager =
                        spanningLinearLayoutManager
                    adapter.submitList(tomData)
                }

            } ?: run {
                showError()
            }


        }

    }

    private fun showData() {
        getViewDataBinding().error.gone()
        getViewDataBinding().loading.gone()
        getViewDataBinding().weatherData.visible()
    }

    private fun showError() {
        getViewDataBinding().error.visible()
        getViewDataBinding().error.tryagain.setOnClickListener {
            sharedViewModel.fragmentReloadData.postValue(
                true
            )
        }
        getViewDataBinding().loading.gone()
        getViewDataBinding().weatherData.gone()
    }

    private fun showloadingState() {
        getViewDataBinding().error.gone()
        getViewDataBinding().loading.visible()
        getViewDataBinding().weatherData.gone()
    }

}