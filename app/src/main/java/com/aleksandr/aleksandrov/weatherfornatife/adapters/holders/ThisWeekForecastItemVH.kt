package com.aleksandr.aleksandrov.weatherfornatife.adapters.holders

import android.view.View
import com.aleksandr.aleksandrov.weatherfornatife.Utils.TimeConvertor.Companion.convertToDayOfWeek
import com.aleksandr.aleksandrov.weatherfornatife.api.models.Day
import com.aleksandr.aleksandrov.weatherfornatife.base_classes.BaseWeatherInfoVH
import kotlinx.android.synthetic.main.forecast_list_item.view.*

/**
 * Created by Alexandrov Alex on 2019-06-01.
 */
class ThisWeekForecastItemVH(itemView: View) : BaseWeatherInfoVH(itemView) {

    override fun bind(item: Day) {
        itemView.tvDayOfWeek.text = convertToDayOfWeek(item.dayInfo[0].dt)
        val temp = item.dayInfo[0].main!!.tempMax.toString() + "/" + item.dayInfo[0].main!!.tempMin
        itemView.tvTemperature.text = temp
    }
}
