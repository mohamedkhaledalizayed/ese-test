package com.neqabty.recruitment.modules.personalinfo.data.model.engineerdata


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class ZoneModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)