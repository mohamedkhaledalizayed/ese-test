package com.neqabty.recruitment.modules.personalinfo.domain.entity.company



data class CompanyEntity(
    val about: String,
    val email: String,
    val established: Int,
    val faxNumber: String,
    val headquarters: String,
    val id: Int,
    val linkedInLink: String,
    val mobileNumber: String,
    val name: String,
    val numberOfEmployees: Int,
    val ownerName: String,
    val phoneNumber: String,
    val website: String
)