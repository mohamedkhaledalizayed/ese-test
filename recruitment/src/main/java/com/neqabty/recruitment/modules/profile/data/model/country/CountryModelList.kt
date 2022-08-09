package com.neqabty.recruitment.modules.profile.data.model.country


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class CountryModelList(
    @SerializedName("cities")
    val cities: List<CountryModel>
)