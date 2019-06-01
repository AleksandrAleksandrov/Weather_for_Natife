package com.aleksandr.aleksandrov.weatherfornatife.adapters.holders

import android.view.View
import com.aleksandr.aleksandrov.weatherfornatife.Utils.TimeConvertor
import com.aleksandr.aleksandrov.weatherfornatife.adapters.WeaterSelectedDayAdapter
import com.aleksandr.aleksandrov.weatherfornatife.api.models.Day
import com.aleksandr.aleksandrov.weatherfornatife.base_classes.BaseWeatherInfoVH
import kotlinx.android.synthetic.main.forecast_first_item.view.*

/**
 * Created by Alexandrov Alex on 2019-06-01.
 */
class ThisWeekForecastFirstVH(itemView: View) : BaseWeatherInfoVH(itemView) {

    override fun bind (item: Day) {
        val adapter = WeaterSelectedDayAdapter()
        adapter.setAdapterData(item.dayInfo)
        itemView.rvSelectedDay.adapter = adapter

        itemView.tvCurrentTemperature.setText("" + TimeConvertor.convertToFullDate(item.dayInfo[0].dt))
    }

}
