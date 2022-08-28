package com.neqabty.recruitment.modules.profile.data.model.engineerskills


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class Experience(
    @SerializedName("company_name")
    val companyName: String,
    @SerializedName("end_date")
    val endDate: Any,
    @SerializedName("engineer")
    val engineer: String,
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