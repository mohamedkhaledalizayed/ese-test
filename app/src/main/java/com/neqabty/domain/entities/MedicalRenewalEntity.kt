package com.neqabty.domain.entities

data class MedicalRenewalEntity(
        var contact: MedicalRenewalEntity.ContactData? = null,
        var followers: List<MedicalRenewalEntity.FollowerItem>? = null
) {

    data class ContactData(
            var name: String = "",
            var contactID: String = "",
            var syndicateName: String = ""
    )

    data class FollowerItem(
            var name: String = "",
            var id: Int = 0,
            var isDeleted: Boolean = false,
            var birthDate: String = "",
            var pic: String? = ""
    )
}