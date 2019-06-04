package com.aleksandr.aleksandrov.weatherfornatife.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aleksandr.aleksandrov.weatherfornatife.R
import com.aleksandr.aleksandrov.weatherfornatife.adapters.holders.ThisWeekForecastFirstVH
import com.aleksandr.aleksandrov.weatherfornatife.adapters.holders.ThisWeekForecastItemVH
import com.aleksandr.aleksandrov.weatherfornatife.api.models.Day
import com.aleksandr.aleksandrov.weatherfornatife.base_classes.BaseRecyclerViewAdapter
import com.aleksandr.aleksandrov.weatherfornatife.base_classes.BaseWeatherInfoVH
import kotlinx.android.synthetic.main.forecast_first_item.view.*
import org.androidannotations.annotations.EBean

/**
 * Created by Alexandrov Alex on 2019-06-01.
 */
@EBean
open class WeatherAdapter : BaseRecyclerViewAdapter<Day, BaseWeatherInfoVH>() {

    companion object {
        const val MAIN_INFO_TYPE = 0
        const val LIST_INFO_TYPE = 1
        const val ADDITIONAL_ROWS = 1
    }

    lateinit var selectCoordinates: View.OnClickListener

    var selectedIndex: Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseWeatherInfoVH {
        return if (viewType == MAIN_INFO_TYPE) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.forecast_first_item, parent, false)
            ThisWeekForecastFirstVH(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.forecast_list_item, parent, false)
            ThisWeekForecastItemVH(view)
        }
    }

    override fun onBindViewHolder(holder: BaseWeatherInfoVH, position: Int) {
        if (position == 0) {
            (holder as ThisWeekForecastFirstVH).bind(data[selectedIndex])
            (holder as ThisWeekForecastFirstVH).itemView.ivChooseLocation.setOnClickListener(View.OnClickListener {
                selectCoordinates.onClick(it)
            })
        } else {
            (holder as ThisWeekForecastItemVH).bind(data[position - ADDITIONAL_ROWS])
            holder.itemView.tag = position - ADDITIONAL_ROWS
            holder.itemView.setOnClickListener {
                selectedIndex = it.tag as Int
                notifyItemChanged(0)
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            MAIN_INFO_TYPE
        } else {
            LIST_INFO_TYPE
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + ADDITIONAL_ROWS
    }

    fun setSelectCoordinate(listener : View.OnClickListener) {
        selectCoordinates = listener
    }
}
