package com.aleksandr.aleksandrov.weatherfornatife.base_classes

import android.support.v7.widget.RecyclerView
import android.view.View
import com.aleksandr.aleksandrov.weatherfornatife.api.models.Day

/**
 * Created by Alexandrov Alex on 2019-06-01.
 */
open class BaseWeatherInfoVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

    open fun bind(item: Day) {

    }
}
