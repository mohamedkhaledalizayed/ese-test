package com.neqabty.presentation.entities

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MedicalRenewalUI(
        var oldRefId: String? = "",
        var requestStatus: Int? = 0,
        var healthCareStatus: Int? = 0,
        var engineerStatus: Int? = 0,
        var contact: ContactData? = null,
        var followers: MutableList<FollowerItem>? = null,
        var relations: List<RelationItem>? = null,
        var rejectionMsg: String? = ""
) : Parcelable {

    fun <T : Parcelable> deepClone(objectToClone: T): T? {
        var parcel: Parcel? = null
        return try {
            parcel = Parcel.obtain()
            parcel.writeParcelable(objectToClone, 0)
            parcel.setDataPosition(0)
            parcel.readParcelable(objectToClone::class.java.classLoader)
        } finally {
            //it is important to recyle parcel and free up resources once done.
            parcel?.recycle()
        }
    }

    @Parcelize
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
    ) : Parcelable

    @Parcelize
    data class FollowerItem(
            var name: String? = "",
            var id: Int? = 0,
            var isDeleted: Boolean? = false,
            var birthDate: String? = "",
            var pic: String? = "",
            var attachments: MutableList<String>? = mutableListOf(),
            var isNew: Boolean? = false,
            var isEdited: Boolean? = false,
            var mobile: String? = null,
            var nationalId: String? = null,
            var relationType: String? = null,
            var relationTypeName: String? = null,
            var gender: String? = "M"
    ) : Parcelable{
        override fun toString(): String {
            return name ?: ""
        }
    }


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