package com.neqabty.recruitment.modules.profile.data.model.addarea


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Zone(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)