package com.neqabty.presentation.entities

data class ClaimingValidationUI(
        var code: Int = 0,
        var engineerName: String = "",
        var message: String?,
        var oldbenid: String?
)