package com.neqabty.presentation.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MedicalRenewalUI(
        var contact: ContactData? = null,
        var followers: MutableList<FollowerItem>? = null
) : Parcelable {

    @Parcelize
    data class ContactData(
            var name: String = "",
            var contactID: String = "",
            var syndicateName: String = ""
    ) : Parcelable

    @Parcelize
    data class FollowerItem(
            var name: String = "",
            var id: Int = 0,
            var isDeleted: Boolean = false,
            var birthDate: String = "",
            var pic: String? = ""
    ) : Parcelable
}