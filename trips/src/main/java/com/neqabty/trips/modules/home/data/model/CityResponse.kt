package com.neqabty.trips.modules.home.data.model

import com.google.gson.annotations.SerializedName

data class CityResponse(@SerializedName("cities") val cities: List<CityModel>)
