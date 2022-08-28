package com.neqabty.recruitment.modules.profile.data.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Engineercourse(
    @SerializedName("course")
    val course: Int,
    @SerializedName("end_date")
    val endDate: String,
    @SerializedName("engineer")
    val engineer: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("present_flag")
    val presentFlag: Boolean,
    @SerializedName("start_date")
    val startDate: String
)