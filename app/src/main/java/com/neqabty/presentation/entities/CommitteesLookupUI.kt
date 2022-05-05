package com.neqabty.presentation.entities

data class CommitteesLookupUI(
    var degreesList: List<String>,
    var maritalStatusList: List<String>,
    var committeesList: List<CommitteeItem>,
    var sectionsList: List<SectionItem>,
    var syndicatesList: List<SyndicateItem>
){
    data class CommitteeItem(
        var id: Int = 0,
        var name: String?
    ){
        override fun toString(): String {
            return name ?: ""
        }
    }

    data class SectionItem(
        var id: Int = 0,
        var name: String?,
        var nameEn: String?
    ){
        override fun toString(): String {
            return name ?: ""
        }
    }

    data class SyndicateItem(
        var id: Int = 0,
        var name: String?,
        var nameEn: String?
    ){
        override fun toString(): String {
            return name ?: ""
        }
    }
}