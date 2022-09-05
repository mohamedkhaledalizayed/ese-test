package com.neqabty.recruitment.modules.personalinfo.data.model.universities


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep


@Keep
data class UniversityLisModel(
    @SerializedName("universities")
    val universities: List<UniversityModel>
)