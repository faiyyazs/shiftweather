package com.shiftweather.presentation.forecast.adapter

import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.viewpager.widget.PagerAdapter
import com.shiftweather.R
import com.shiftweather.core.presentation.inflate
import com.shiftweather.presentation.model.WeatherDate
import com.shiftweather.presentation.utils.getTitleDateFormat


class DatesAdapter :
    PagerAdapter() {


    private val mData = mutableListOf<WeatherDate>()

    fun setDates(dates: List<WeatherDate>) {
        mData.clear()
        mData.addAll(dates)
        notifyDataSetChanged()
    }

    fun getDates(): List<WeatherDate> {
        return mData
    }

    override fun isViewFromObject(container: View, view: Any): Boolean {
        return container === view
    }


    override fun instantiateItem(parent: ViewGroup, position: Int): Any {
        val row = parent.inflate(R.layout.layout_item_date)
        row.findViewById<AppCompatTextView>(R.id.date).text =
            getTitleDateFormat(mData[position].date)
        parent.addView(row)
        return row
    }


    override fun destroyItem(container: ViewGroup, position: Int, view: Any) {
        container.removeView(view as View)
    }

    override fun getCount(): Int {
        return mData.size
    }


}

