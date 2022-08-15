package com.neqabty.data.entities

import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Response

data class MedicalProceduresInquiryLookupsData(
    @field:SerializedName("GovernorateList")
    var governs: List<Govern>? = null,
    @field:SerializedName("PoliceStationList")
    var areas: List<Area>? = null,
    @field:SerializedName("CategoryList")
    var categories: List<ProcedureCategory>? = null,
    @field:SerializedName("RelationTypeList")
    var relationTypes: List<RelationType>? = null
) : Response() {
    data class Govern(
        @field:SerializedName("Id")
        var id: Int = 0,
        @field:SerializedName("ARABICNAME")
        var name: String
    )

    data class Area(
        @field:SerializedName("Id")
        var id: Int = 0,
        @field:SerializedName("StationNameArb")
        var name: String,
        @field:SerializedName("FK_GovID")
        var govId: Int = 0
    )

    data class ProcedureCategory(
        @field:SerializedName("Id")
        var id: Int = 0,
        @field:SerializedName("CategoryName")
        var name: String,
        @field:SerializedName("ParentCategoryId")
        var parentCategoryId: Int? = 0
    )

    data class RelationType(
        @field:SerializedName("Id")
        var id: Int = 0,
        @field:SerializedName("RelationTypeName")
        var name: String
    )
}