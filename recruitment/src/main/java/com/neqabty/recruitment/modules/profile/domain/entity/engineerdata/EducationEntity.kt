package com.neqabty.recruitment.modules.profile.domain.entity.engineerdata



data class EducationEntity(
    val certificate: String,
    val degree: String,
    val endDate: Any,
    val id: Int,
    val presentFlag: Boolean,
    val startDate: String
)