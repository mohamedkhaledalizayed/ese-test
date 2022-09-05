package com.neqabty.recruitment.modules.personalinfo.domain.entity.engineerdata



data class EducationEntity(
    val certificate: String,
    val degree: String,
    val endDate: Any,
    val id: Int,
    val presentFlag: Boolean,
    val startDate: String
)