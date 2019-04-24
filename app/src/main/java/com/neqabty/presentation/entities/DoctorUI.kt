package com.neqabty.presentation.entities

data class DoctorUI(
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
) {
    override fun toString(): String {
        return name ?: ""
    }
}