package com.neqabty.domain.entities

data class DoctorEntity(
    var id: Int = 0,
    var serial: String?,
    var category: String?,
    var profissionCode: String?,
    var name: String?,
    var degree: String?,
    var degreeCode: String?,
    var address: String?,
    var area: String?,
    var areaCode: String?,
    var phoneCode: String?,
    var phoneNumber: String?
)