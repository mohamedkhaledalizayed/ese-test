package com.neqabty.recruitment.modules.personalinfo.data.model.area


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class AreaModelList(
    @SerializedName("zones")
    val zones: List<AreaModel>
)