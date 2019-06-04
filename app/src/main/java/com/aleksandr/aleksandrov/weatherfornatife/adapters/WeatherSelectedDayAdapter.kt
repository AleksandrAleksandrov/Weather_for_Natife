package com.aleksandr.aleksandrov.weatherfornatife.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.aleksandr.aleksandrov.weatherfornatife.R
import com.aleksandr.aleksandrov.weatherfornatife.adapters.holders.HourForecastVH
import com.aleksandr.aleksandrov.weatherfornatife.api.models.DayInfo
import com.aleksandr.aleksandrov.weatherfornatife.base_classes.BaseRecyclerViewAdapter

/**
 * Created by Alexandrov Alex on 2019-06-01.
 */
class WeatherSelectedDayAdapter : BaseRecyclerViewAdapter<DayInfo, HourForecastVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourForecastVH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.hour_forecast_list_item, parent, false)
        return HourForecastVH(view)
    }

    override fun onBindViewHolder(holder: HourForecastVH, position: Int) {
        holder.bind(data[position])
    }

}
