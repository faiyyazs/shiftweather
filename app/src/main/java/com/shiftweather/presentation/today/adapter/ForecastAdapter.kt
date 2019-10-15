package com.shiftweather.presentation.today.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shiftweather.R
import com.shiftweather.core.presentation.inflate
import com.shiftweather.presentation.utils.getDegreesRepresentation
import com.shiftweather.presentation.utils.getForecastDateformat
import com.shiftweather.presentation.utils.icon
import com.shiftweather.presentation.model.WeatherData
import kotlinx.android.synthetic.main.layout_item_forecast.view.*


class ForecastAdapter :
    ListAdapter<WeatherData, ForecastAdapter.ViewHolder>(PostDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

    inner class ViewHolder(parent: ViewGroup) :

        RecyclerView.ViewHolder(parent.inflate(R.layout.layout_item_forecast)) {

        fun bind(item: WeatherData) {
            itemView.title.text =
                getForecastDateformat(item.date)
            itemView.weatherType.setImageResource(item.weather.phenomenon.icon())
            itemView.txtMax.text =item.weather.tempmax?.let {
                getDegreesRepresentation(
                    itemView.context,
                    item.weather.tempmax
                )
            }
            itemView.txtMin.text =item.weather.tempmin?.let {
                getDegreesRepresentation(
                    itemView.context,
                    item.weather.tempmin
                )
            }

        }
    }
}

private class PostDiffCallback : DiffUtil.ItemCallback<WeatherData>() {
    override fun areItemsTheSame(oldItem: WeatherData, newItem: WeatherData): Boolean =
        oldItem.date == newItem.date

    override fun areContentsTheSame(oldItem: WeatherData, newItem: WeatherData): Boolean =
        oldItem == newItem
}