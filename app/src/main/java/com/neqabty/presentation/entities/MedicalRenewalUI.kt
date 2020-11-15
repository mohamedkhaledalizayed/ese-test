package com.neqabty.presentation.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MedicalRenewalUI(
        var oldRefId: String? = "",
        var isSubscribed: Boolean? = false,
        var requestStatus: Int? = -1,
        var contact: ContactData? = null,
        var followers: MutableList<FollowerItem>? = null,
        var relations: List<RelationItem>? = null,
        var rejectionMsg: String? = ""
) : Parcelable {

    @Parcelize
    data class ContactData(
            var name: String? = "",
            var syndicateName: String? = ""
    ) : Parcelable

    @Parcelize
    data class FollowerItem(
            var name: String = "",
            var id: Int = 0,
            var isDeleted: Boolean = false,
            var birthDate: String = "",
            var pic: String? = "",
            var attachments: MutableList<String> = mutableListOf(),
            var isNew: Boolean = false,
            var mobile: String? = null,
            var nationalId: String? = null,
            var relationType: String? = null,
            var gender: String? = null
    ) : Parcelable


    @Parcelize
    data class RelationItem(
            var id: String? = "",
            var name: String? = ""
    ) : Parcelable{
        override fun toString(): String {
            return name ?: ""
        }
    }

}