package com.neqabty.domain.entities

data class MedicalRenewalEntity(
        var oldRefId: String? = "",
        var engineerStatus: Int? = 0,
        var requestStatus: Int? = 0,
        var healthCareStatus: Int? = 0,
        var contact: MedicalRenewalEntity.ContactData? = null,
        var followers: List<MedicalRenewalEntity.FollowerItem>? = null,
        var relations: List<RelationItem>? = null,
        var rejectionMsg: String? = ""
) {

    data class ContactData(
            var name: String? = "",
            var syndicateName: String? = "",
            var isDead: Boolean? = false,
            var address: String? = "",
            var mobile: String? = ""
    )

    data class FollowerItem(
            var name: String? = "",
            var id: Int? = 0,
            var isDeleted: Boolean? = false,
            var birthDate: String? = "",
            var pic: String? = "",
            var attachments: MutableList<String>? = mutableListOf(),
            var mobile: String? = null,
            var nationalId: String? = null,
            var relationType: String? = null,
            var gender: String? = null
    )

    data class RelationItem(
            var id: String? = "",
            var name: String? = ""
    )

}