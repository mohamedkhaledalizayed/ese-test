package com.neqabty.recruitment.modules.profile.data.model.adduniversity


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class University(
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)