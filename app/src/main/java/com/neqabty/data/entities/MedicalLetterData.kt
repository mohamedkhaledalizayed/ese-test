package com.neqabty.data.entities

import com.google.gson.annotations.SerializedName

data class MedicalLetterData(
    @field:SerializedName("TotalCount")
    var totalCount: Int = 0,
    @field:SerializedName("LetterList")
    var letters: List<LetterItem>? = null
) {
    data class LetterItem(
        @field:SerializedName("LetterTypeName")
        var letterTypeName: String? = "",
        @field:SerializedName("IsActive")
        var isActive: Boolean? = true,
        @field:SerializedName("ServiceProviderName")
        var serviceProviderName: String? = "",
        @field:SerializedName("LetterDate")
        var letterDate: String?,
        @field:SerializedName("LetterStatusName")
        var letterStatusName: String?,
        @field:SerializedName("TotalPrice")
        var totalPrice: String?,
        @field:SerializedName("CreationType")
        var creationType: Int?,
        @field:SerializedName("LetterProcedureList")
        var letterProcedures: List<LetterProcedureItem>? = null
    )
    data class LetterProcedureItem(
        @field:SerializedName("LetterProcedureName")
        var letterProcedureName: String?
    )
}