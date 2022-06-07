package com.neqabty.domain.entities

data class MedicalProceduresInquiryLookupsEntity(
    var governs: List<Govern>? = null,
    var areas: List<Area>? = null,
    var categories: List<ProcedureCategory>? = null,
    var relationTypes: List<RelationType>? = null
) {
    data class Govern(
        var id: Int = 0,
        var name: String
    )

    data class Area(
        var id: Int = 0,
        var name: String,
        var govId: Int = 0
    )

    data class ProcedureCategory(
        var id: Int = 0,
        var name: String,
        var parentCategoryId: Int? = 0
    )

    data class RelationType(
        var id: Int = 0,
        var name: String
    )
}