package com.neqabty.recruitment.modules.personalinfo.data.model.addarea


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class AddAreaModel(
    @SerializedName("zone")
    val zone: Zone
)