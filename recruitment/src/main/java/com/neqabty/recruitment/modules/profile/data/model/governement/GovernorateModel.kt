package com.neqabty.recruitment.modules.profile.data.model.governement


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class GovernorateModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)