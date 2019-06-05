package com.aleksandr.aleksandrov.weatherfornatife.adapters.holders

import android.support.v7.widget.RecyclerView
import android.view.View
import com.aleksandr.aleksandrov.weatherfornatife.R
import com.aleksandr.aleksandrov.weatherfornatife.Utils.IconHelper
import com.aleksandr.aleksandrov.weatherfornatife.Utils.TimeConvertor
import com.aleksandr.aleksandrov.weatherfornatife.api.models.DayInfo
import kotlinx.android.synthetic.main.hour_forecast_list_item.view.*

/**
 * Created by Alexandrov Alex on 2019-06-01.
 */
class HourForecastVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(item: DayInfo) {
        itemView.ivIcon.setImageDrawable(itemView.context.resources.getDrawable(IconHelper.weatherIcon(item.weather!![0]!!.icon)))
        itemView.tvHour.text = TimeConvertor.convertToHours(item.dt)
        itemView.tvHourTemperature.text = item.main!!.temp!!.toInt().toString() + itemView.context.resources.getString(R.string.celsius)
    }
}
