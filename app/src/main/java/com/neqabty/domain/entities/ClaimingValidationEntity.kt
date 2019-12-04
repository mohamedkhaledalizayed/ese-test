package com.neqabty.domain.entities

data class ClaimingValidationEntity(
        var code: Int = 0,
        var engineerName: String = "",
        var message: String?
)