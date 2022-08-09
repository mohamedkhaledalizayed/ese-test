package com.neqabty.recruitment.modules.profile.domain.entity.engineerdata



data class ExperienceEntity(
    val companyName: String,
    val endDate: Any,
    val id: Int,
    val jobDescription: String,
    val jobEmploymentType: String,
    val jobTitle: String,
    val presentFlag: Boolean,
    val startDate: String
)