package com.shiftweather.presentation.city.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shiftweather.R
import com.shiftweather.core.presentation.inflate
import com.shiftweather.presentation.model.City
import com.shiftweather.presentation.utils.icon
import kotlinx.android.synthetic.main.layout_item_city.view.*


class CityAdapter constructor(private val itemClick: (City) -> Unit) :
    ListAdapter<City, CityAdapter.ViewHolder>(CityDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))

    inner class ViewHolder(parent: ViewGroup) :

        RecyclerView.ViewHolder(parent.inflate(R.layout.layout_item_city)) {

        fun bind(item: City) {

            itemView.cityName.text = item.name

            item.day?.let { day ->
                day.phenomenon.let {
                    itemView.dayWeatherType.setImageResource(it.icon())
                    itemView.tempMax.text = day.tempmax?.toInt().toString()
                }
            }


            item.night?.let { night ->
                night.phenomenon.let {
                    itemView.nightWeatherType.setImageResource(it.icon())
                    itemView.tempMin.text = night.tempmin?.toInt().toString()
                }
            }

        }
    }
}

private class CityDiffCallback : DiffUtil.ItemCallback<City>() {
    override fun areItemsTheSame(oldItem: City, newItem: City): Boolean =
        oldItem.name == newItem.name

    override fun areContentsTheSame(oldItem: City, newItem: City): Boolean =
        oldItem == newItem
}