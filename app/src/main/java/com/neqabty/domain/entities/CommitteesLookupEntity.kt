package com.neqabty.domain.entities

data class CommitteesLookupEntity(
    var degreesList: List<String>,
    var maritalStatusList: List<String>,
    var committeesList: List<CommitteeItem>,
    var sectionsList: List<SectionItem>,
    var syndicatesList: List<SyndicateItem>
){
    data class CommitteeItem(
        var id: Int = 0,
        var name: String?
    )

    data class SectionItem(
        var id: Int = 0,
        var name: String?,
        var nameEn: String?
    )

    data class SyndicateItem(
        var id: Int = 0,
        var name: String?,
        var nameEn: String?
    )
}