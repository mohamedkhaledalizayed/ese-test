package com.neqabty.recruitment.modules.personalinfo.domain.entity.engineerdata



data class EducationEntity(
    val certificate: String?,
    val degree: String,
    val endDate: String?,
    val id: Int,
    val presentFlag: Boolean,
    val startDate: String
)