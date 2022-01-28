package com.neqabty.domain.entities

data class MedicalRenewalEntity(
        var oldRefId: String? = "",
        var engineerStatus: Int? = 0,
        var requestStatus: Int? = 0,
        var healthCareStatus: Int? = 0,
        var contact: ContactData? = null,
        var followers: List<FollowerItem>? = null,
        var relations: List<RelationItem>? = null,
        var rejectionMsg: String? = "",
        var currentMedYear: String? = ""
) {

    data class ContactData(
            var name: String? = "",
            var syndicateName: String? = "",
            var isDead: Boolean? = false,
            var address: String? = "",
            var mobile: String? = "",
            var nationalId: String? = "",
            var birthDate: String? = "",
            var pic: String? = "",
            var benID: String? = ""
    )

    data class FollowerItem(
            var name: String? = "",
            var name1: String? = "",
            var name2: String? = "",
            var name3: String? = "",
            var name4: String? = "",
            var id: Int? = 0,
            var isDeleted: Boolean? = false,
            var modificationReason: String? = "",
            var birthDate: String? = "",
            var pic: String? = "",
            var attachments: MutableList<String>? = mutableListOf(),
            var mobile: String? = null,
            var nationalId: String? = null,
            var relationType: String? = null,
            var relationTypeName: String? = null,
            var gender: String? = null,
            var lastMedYear: String? = ""
    )

    data class RelationItem(
            var id: String? = "",
            var name: String? = ""
    )

}