package com.neqabty.data.entities

import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Response


data class CommitteesLookupData(
    @field:SerializedName("degree")
    var degreesList: List<String>,
    @field:SerializedName("marital_status\t")
    var maritalStatusList: List<String>,
    @field:SerializedName("committees")
    var committeesList: List<CommitteeItem>,
    @field:SerializedName("sections")
    var sectionsList: List<SectionItem>,
    @field:SerializedName("syndicates")
    var syndicatesList: List<SyndicateItem>
) : Response(){
    data class CommitteeItem(
        @field:SerializedName("id")
        var id: Int = 0,
        @field:SerializedName("name")
        var name: String?
    )

    data class SectionItem(
        @field:SerializedName("id")
        var id: Int = 0,
        @field:SerializedName("name")
        var name: String?,
        @field:SerializedName("name_en")
        var nameEn: String?
    )

    data class SyndicateItem(
        @field:SerializedName("id")
        var id: Int = 0,
        @field:SerializedName("sub_syndicate_name_ar")
        var name: String?,
        @field:SerializedName("sub_syndicate_name_en")
        var nameEn: String?
    )
}