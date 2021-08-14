package com.neqabty.presentation.entities

data class MedicalLetterUI(
    var totalCount: Int = 0,
    var letters: MutableList<LetterItem>? = null
) {
    data class LetterItem(
        var letterTypeName: String = "",
        var serviceProviderName: String = "",
        var letterDate: String?,
        var letterStatusName: String?,
        var totalPrice: String?,
        var creationType: Int?,
        var creationTypeName: String = "",
        var letterProcedures: List<LetterProcedureItem>? = null
    )
    data class LetterProcedureItem(
        var letterProcedureName: String?
    )
}