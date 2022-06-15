package com.neqabty.healthcare.modules.search.data.model


import com.google.gson.annotations.SerializedName
import java.util.*

data class Profession(
    @SerializedName("created_at")
    val createdAt: Date? = null ,
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("profession_name")
    val professionName: String = "",
    @SerializedName("updated_at")
    val updatedAt: Date? = null
)