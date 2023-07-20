package com.neqabty.healthcare.medicalnetwork.data.model.search


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class Degree(
    @SerializedName("degree_name")
    val degreeName: String?,
    @SerializedName("id")
    val id: Int
)