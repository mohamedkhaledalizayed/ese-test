package com.neqabty.courses.home.data.model.courses


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Lab(
    @SerializedName("entity")
    val entity: Entity,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image")
    val image: String,
    @SerializedName("name")
    val name: String
)