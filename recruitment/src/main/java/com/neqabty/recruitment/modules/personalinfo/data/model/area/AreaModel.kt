package com.neqabty.recruitment.modules.personalinfo.data.model.area


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class AreaModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)