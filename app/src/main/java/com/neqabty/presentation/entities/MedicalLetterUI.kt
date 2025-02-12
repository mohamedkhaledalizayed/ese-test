package com.neqabty.presentation.entities

data class MedicalLetterUI(
    var totalCount: Int = 0,
    var letters: MutableList<LetterItem>? = null
) {
    data class LetterItem(
        var id: String? = "",
        var name: String? = "",
        var letterTypeName: String? = "",
        var isActive: Boolean? = true,
        var serviceProviderName: String? = "",
        var letterDate: String?,
        var letterStatusName: String?,
        var totalPrice: String?,
        var creationType: Int?,
        var creationTypeName: String = "",
        var report: String?,
        var letterProcedures: List<LetterProcedureItem>? = null
    )
    data class LetterProcedureItem(
        var letterProcedureName: String?
    )
}