package com.neqabty.healthcare.sustainablehealth.medicalnetwork.data.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import java.util.*
@Keep
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