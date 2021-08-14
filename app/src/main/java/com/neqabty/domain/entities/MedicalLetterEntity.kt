package com.neqabty.domain.entities

data class MedicalLetterEntity(
    var totalCount: Int = 0,
    var letters: List<LetterItem>? = null
) {
    data class LetterItem(
        var letterTypeName: String = "",
        var serviceProviderName: String = "",
        var letterDate: String?,
        var letterStatusName: String?,
        var totalPrice: String?,
        var creationType: Int?,
        var letterProcedures: List<LetterProcedureItem>? = null
    )
    data class LetterProcedureItem(
        var letterProcedureName: String?
    )
}