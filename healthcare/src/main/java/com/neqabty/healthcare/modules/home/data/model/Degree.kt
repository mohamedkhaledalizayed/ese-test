package com.neqabty.healthcare.modules.home.data.model


import com.google.gson.annotations.SerializedName
import java.util.*

data class Degree(
    @SerializedName("created_at")
    val createdAt: Date? = null ,
    @SerializedName("degree_name")
    val degreeName: String = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("updated_at")
    val updatedAt: Date? = null
)