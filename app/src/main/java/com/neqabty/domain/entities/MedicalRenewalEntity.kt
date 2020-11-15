package com.neqabty.domain.entities

data class MedicalRenewalEntity(
        var oldRefId: String? = "",
        var contact: MedicalRenewalEntity.ContactData? = null,
        var followers: List<MedicalRenewalEntity.FollowerItem>? = null
) {

    data class ContactData(
            var name: String = "",
            var syndicateName: String? = "",
            var isNew: Boolean? = false,
            var requestStatus: Int? = 0
    )

    data class FollowerItem(
            var name: String = "",
            var id: Int = 0,
            var isDeleted: Boolean = false,
            var birthDate: String = "",
            var pic: String? = "",
            var attachments: MutableList<String>? = mutableListOf()
    )
}