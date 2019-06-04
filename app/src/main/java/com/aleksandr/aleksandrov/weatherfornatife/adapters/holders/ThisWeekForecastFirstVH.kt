package com.aleksandr.aleksandrov.weatherfornatife.adapters.holders

import android.view.View
import com.aleksandr.aleksandrov.weatherfornatife.R
import com.aleksandr.aleksandrov.weatherfornatife.Utils.IconHelper
import com.aleksandr.aleksandrov.weatherfornatife.Utils.TimeConvertor
import com.aleksandr.aleksandrov.weatherfornatife.adapters.WeatherSelectedDayAdapter
import com.aleksandr.aleksandrov.weatherfornatife.api.models.Day
import com.aleksandr.aleksandrov.weatherfornatife.base_classes.BaseWeatherInfoVH
import kotlinx.android.synthetic.main.forecast_first_item.view.*
import kotlinx.android.synthetic.main.layout_weather_state_set.view.*

/**
 * Created by Alexandrov Alex on 2019-06-01.
 */
class ThisWeekForecastFirstVH(itemView: View) : BaseWeatherInfoVH(itemView) {

    override fun bind (item: Day) {
        val adapter = WeatherSelectedDayAdapter()
        adapter.setAdapterData(item.dayInfo)
        itemView.rvSelectedDay.adapter = adapter
        val temp = item.dayInfo[0].main!!.tempMax!!.toInt().toString() + itemView.context.resources.getString(R.string.celsius) + "/" +
        item.dayInfo[0].main!!.tempMin!!.toInt().toString() + itemView.context.resources.getString(R.string.celsius)
        itemView.tvMaxMinTemp.text = temp
        val humidity = item.dayInfo[0].main!!.humidity!!.toInt().toString() + "%"
        val windSpeed = item.dayInfo[0].wind!!.speed!!.toInt().toString() + itemView.context.resources.getString(R.string.m_sec)
        itemView.tvHumidity.text = humidity
        itemView.tvWind.text = windSpeed
        itemView.tvWind.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_wind, 0, IconHelper.degreeIcon(item.dayInfo[0].wind!!.deg), 0);
        itemView.ivWeatherIcon.setImageDrawable(itemView.context.resources.getDrawable(IconHelper.weatherIcon(item.dayInfo[0].weather!!.get(0)!!.icon)))
        itemView.tvCurrentTemperature.setText("" + TimeConvertor.convertToFullDate(item.dayInfo[0].dt))
    }

}
