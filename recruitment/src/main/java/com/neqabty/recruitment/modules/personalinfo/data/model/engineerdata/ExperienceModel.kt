package com.neqabty.recruitment.modules.personalinfo.data.model.engineerdata


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class ExperienceModel(
    @SerializedName("company_name")
    val companyName: String,
    @SerializedName("end_date")
    val endDate: String?,
    @SerializedName("id")
    val id: Int,
    @SerializedName("job_description")
    val jobDescription: String,
    @SerializedName("job_employment_type")
    val jobEmploymentType: String,
    @SerializedName("job_title")
    val jobTitle: String,
    @SerializedName("present_flag")
    val presentFlag: Boolean,
    @SerializedName("start_date")
    val startDate: String
)