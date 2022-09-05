package com.neqabty.recruitment.modules.personalinfo.data.model.nationalities


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class NationalitiesModelList(
    @SerializedName("nationalities")
    val nationalities: List<NationalityModel>
)