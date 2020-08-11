package com.neqabty.domain.entities

data class InquireUpdateUserDataEntity(
    var oldRefID: String = "",
    var fullName: String?,
    var address: String?,
    var phone: String?,
    var mobile: String?,
    var email: String?,
    var birthdate: String?,
    var graduationyear: String?,
    var passportNumber: String?,
    var nationalID: String?
)