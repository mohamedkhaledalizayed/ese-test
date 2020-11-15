package com.neqabty.presentation.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MedicalRenewalUI(
        var oldRefId: String? = "",
        var contact: ContactData? = null,
        var followers: MutableList<FollowerItem>? = null
) : Parcelable {

    @Parcelize
    data class ContactData(
            var name: String = "",
            var syndicateName: String? = "",
            var isNew: Boolean? = false,
            var requestStatus: Int? = 0
    ) : Parcelable

    @Parcelize
    data class FollowerItem(
            var name: String = "",
            var id: Int = 0,
            var isDeleted: Boolean = false,
            var birthDate: String = "",
            var pic: String? = "",
            var attachments: MutableList<String> = mutableListOf(),
            var isNew: Boolean = false
    ) : Parcelable
}