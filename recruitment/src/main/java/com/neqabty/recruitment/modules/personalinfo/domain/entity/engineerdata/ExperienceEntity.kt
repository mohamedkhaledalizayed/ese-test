package com.neqabty.recruitment.modules.personalinfo.domain.entity.engineerdata



data class ExperienceEntity(
    val companyName: String,
    val endDate: String?,
    val id: Int,
    val jobDescription: String,
    val jobEmploymentType: String,
    val jobTitle: String,
    val presentFlag: Boolean,
    val startDate: String
)