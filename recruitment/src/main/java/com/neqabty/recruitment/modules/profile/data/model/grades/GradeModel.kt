package com.neqabty.recruitment.modules.profile.data.model.grades


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class GradeModel(
    @SerializedName("id")
    val id: Int,
    @SerializedName("value")
    val value: String
)