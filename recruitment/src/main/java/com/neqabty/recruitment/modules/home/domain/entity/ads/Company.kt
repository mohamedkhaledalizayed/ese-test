package com.neqabty.recruitment.modules.home.domain.entity.ads



data class Company(
    val about: String,
    val email: String,
    val faxNumber: String,
    val headquarters: String,
    val linkedInLink: String,
    val mobileNumber: String,
    val name: String,
    val numberOfEmployees: Int,
    val ownerName: String,
    val phoneNumber: String,
    val website: String
)