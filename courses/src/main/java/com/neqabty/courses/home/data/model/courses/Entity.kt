package com.neqabty.courses.home.data.model.courses


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Entity(
    @SerializedName("code")
    val code: String,
    @SerializedName("name")
    val name: String
)