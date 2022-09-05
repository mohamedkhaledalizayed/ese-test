package com.neqabty.recruitment.modules.personalinfo.data.model.maritalstatus


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class MaritalStatusModelList(
    @SerializedName("maritalstatuses")
    val maritalstatuses: List<MaritalStatusModel>
)