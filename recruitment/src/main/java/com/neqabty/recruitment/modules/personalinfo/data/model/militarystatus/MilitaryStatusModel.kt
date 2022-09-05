package com.neqabty.recruitment.modules.personalinfo.data.model.militarystatus


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class MilitaryStatusModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)