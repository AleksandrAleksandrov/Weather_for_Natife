package com.aleksandr.aleksandrov.weatherfornatife.adapters.holders

import android.view.View
import com.aleksandr.aleksandrov.weatherfornatife.R
import com.aleksandr.aleksandrov.weatherfornatife.Utils.IconHelper
import com.aleksandr.aleksandrov.weatherfornatife.Utils.TimeConvertor.Companion.convertToDayOfWeek
import com.aleksandr.aleksandrov.weatherfornatife.api.models.Day
import com.aleksandr.aleksandrov.weatherfornatife.base_classes.BaseWeatherInfoVH
import kotlinx.android.synthetic.main.forecast_list_item.view.*

/**
 * Created by Alexandrov Alex on 2019-06-01.
 */
class ThisWeekForecastItemVH(itemView: View) : BaseWeatherInfoVH(itemView) {

    override fun bind(item: Day) {
        itemView.ivIcon.setImageDrawable(itemView.context.resources.getDrawable(IconHelper.weatherIcon(item.dayInfo[0].weather!!.get(0)!!.icon)))
        itemView.tvDayOfWeek.text = convertToDayOfWeek(item.dayInfo[0].dt)
        val temp = item.dayInfo[0].main!!.tempMax!!.toInt().toString() + itemView.context.resources.getString(R.string.celsius) + "/" + item.dayInfo[0].main!!.tempMin!!.toInt() + itemView.context.resources.getString(R.string.celsius)
        itemView.tvTemperature.text = temp
    }
}
