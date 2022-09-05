package com.neqabty.recruitment.modules.personalinfo.data.model.engineerdata


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class EducationModel(
    @SerializedName("certificate")
    val certificate: String,
    @SerializedName("degree")
    val degree: String,
    @SerializedName("department")
    val department: Department,
    @SerializedName("end_date")
    val endDate: Any,
    @SerializedName("engineer")
    val engineer: Engineer,
    @SerializedName("id")
    val id: Int,
    @SerializedName("present_flag")
    val presentFlag: Boolean,
    @SerializedName("start_date")
    val startDate: String
)