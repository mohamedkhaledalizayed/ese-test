package com.neqabty.domain.entities

data class InquireUpdateUserDataEntity(
        var id: Int = 0,
        var oldRefID: String = "",
        var fullName: String?,
        var nationalID: String?,
        var nationalVerified: String?,
        var phoneNumber: String?,
        var phoneCode: String?,
        var phoneVerified: String?,
        var birthdate: String?,
        var createdAt: String?,
        var updatedAt: String?
)