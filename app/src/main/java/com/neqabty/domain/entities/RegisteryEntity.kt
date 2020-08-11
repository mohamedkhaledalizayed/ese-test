package com.neqabty.domain.entities

data class RegisteryEntity(
    var statusCode: Int = 0,
    var registryDataID: String? = "",
    var registryEngineerID: String?,
    var engID: String?,
    var contactID: String?,
    var fullName: String?,
    var lastRenewYear: String?,
    var registryTypeID: String?,
    var regDataStatusID: String?,
    var isOwner: Int?,
    var birthDate: String?,
    var mobile: String?,
    var registerOffice: String?,
    var msg: String?
)