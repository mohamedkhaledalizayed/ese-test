package com.neqabty.domain.entities

data class MedicalComplaintEntity(
        var resultType: String = "",
        var requestID: String = "",
        var msg: String? = "",
        var isSuccess: Boolean = false
)