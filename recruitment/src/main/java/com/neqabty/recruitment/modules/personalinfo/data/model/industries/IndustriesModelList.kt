package com.neqabty.recruitment.modules.personalinfo.data.model.industries


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class IndustriesModelList(
    @SerializedName("industries")
    val industries: List<IndustryModel>
)