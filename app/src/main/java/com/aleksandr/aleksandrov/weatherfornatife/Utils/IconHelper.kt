package com.aleksandr.aleksandrov.weatherfornatife.Utils

import com.aleksandr.aleksandrov.weatherfornatife.R

/**
 * Created by Alexandrov Alex on 2019-06-02.
 */
class IconHelper {

    companion object {

        fun weatherIcon(icon: String?): Int {
            when(icon) {
                "01d" -> R.drawable.ic_white_day_bright
                "01n" -> R.drawable.ic_white_night_bright

                "02d" -> R.drawable.ic_white_day_cloudy
                "02n" -> R.drawable.ic_white_night_cloudy

                "03d" -> R.drawable.ic_white_day_cloudy
                "03n" -> R.drawable.ic_white_night_cloudy

                "04d" -> R.drawable.ic_white_day_cloudy
                "04n" -> R.drawable.ic_white_night_cloudy

                "09d" -> R.drawable.ic_white_day_shower
                "09n" -> R.drawable.ic_white_night_shower

                "10d" -> R.drawable.ic_white_day_rain
                "10n" -> R.drawable.ic_white_night_rain

                "11d" -> R.drawable.ic_white_day_thunder
                "11n" -> R.drawable.ic_white_night_thunder

                //TODO there are no icons for the rest
                "13d" -> R.drawable.ic_white_day_thunder
                "13n" -> R.drawable.ic_white_night_thunder

                "50d" -> R.drawable.ic_white_day_thunder
                "50n" -> R.drawable.ic_white_night_thunder
            }

            return R.drawable.ic_white_day_bright
        }

        fun degreeIcon(degree: Double?): Int {
            if (degree == null) return R.drawable.ic_icon_wind_n

            if (degree > 337.5) return R.drawable.ic_icon_wind_n
            if (degree > 292.5) return R.drawable.ic_icon_wind_wn
            if (degree > 247.5) return R.drawable.ic_icon_wind_w
            if (degree > 202.5) return R.drawable.ic_icon_wind_ws
            if (degree > 157.5) return R.drawable.ic_icon_wind_s
            if (degree > 122.5) return R.drawable.ic_icon_wind_se
            if (degree > 67.5) return R.drawable.ic_icon_wind_e
            if (degree > 22.5) return R.drawable.ic_icon_wind_ne

            return R.drawable.ic_icon_wind_n
        }
    }

}
