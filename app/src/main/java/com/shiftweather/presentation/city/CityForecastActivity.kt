package com.shiftweather.presentation.city

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shiftweather.R
import com.shiftweather.core.presentation.ui.BaseActivity
import com.shiftweather.databinding.ActivityCityweatherBinding
import com.shiftweather.presentation.city.adapter.CityAdapter
import com.shiftweather.presentation.model.City
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*


class CityForecastActivity : BaseActivity<ActivityCityweatherBinding,CityViewModel>(){


    private val activityModel: CityViewModel by viewModel()
    private val cityItemClick: (City) -> Unit =
        {  }
    private val adapter = CityAdapter(cityItemClick)


    override fun getLayoutId(): Int {
        return R.layout.activity_cityweather
    }

    override fun getViewModel(): CityViewModel {
        return activityModel
    }


    override fun setUp(savedInstanceState: Bundle?) {
        getViewDataBinding().recyclerViewCities.adapter = adapter
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        getViewDataBinding().recyclerViewCities.layoutManager = linearLayoutManager
        getViewDataBinding().toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        intent?.let {
            val cityDataList = it.getParcelableArrayListExtra<City>("key")
            cityDataList?.let {
                adapter.submitList(it)
            }?: run{

            }
        }

    }

    companion object{

        fun newIntent(context: Context,cities: List<City>): Intent {
            val intent = Intent(context, CityForecastActivity::class.java)
            intent.putParcelableArrayListExtra("key",cities as ArrayList<out Parcelable>)
            return intent
        }
    }


}