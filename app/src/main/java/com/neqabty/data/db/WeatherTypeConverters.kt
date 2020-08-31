package com.neqabty.data.db

import androidx.room.TypeConverter
import timber.log.Timber

object WeatherTypeConverters {
    @TypeConverter
    @JvmStatic
    fun stringToIntList(data: String?): List<Int>? {
        return data?.let {
            it.split(",").map {
                try {
                    it.toInt()
                } catch (ex: NumberFormatException) {
                    Timber.e(ex, "Cannot convert $it to number")
                    null
                }
            }
        }?.filterNotNull()
    }

    @TypeConverter
    @JvmStatic
    fun intListToString(ints: List<Int>?): String? {
        return ints?.joinToString(",")
    }

//    @TypeConverter
//    @JvmStatic
//    fun listToJson(value: List<WeatherData.WeatherItem>?): String {
//
//        return Gson().toJson(value)
//    }
//
//    @TypeConverter
//    @JvmStatic
//    fun jsonToList(value: String): List<WeatherData.WeatherItem>? {
//        val objects = Gson().fromJson(value, Array<WeatherData.WeatherItem>::class.java) as List<WeatherData.WeatherItem>
//        return objects.toList()
//    }
}
