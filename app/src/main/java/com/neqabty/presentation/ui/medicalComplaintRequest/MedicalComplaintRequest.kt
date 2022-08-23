package com.neqabty.presentation.ui.medicalComplaintRequest

import android.os.Parcel
import android.os.Parcelable
import com.neqabty.domain.entities.AttachmentEntity
import com.neqabty.presentation.ui.medicalCategories.MedicalCategoryUI

data class MedicalComplaintRequest(
    var name: String,
    var mobile: String,
    var userNumber: String,
    var benId: String,
    var description: String,
    var branchProfileId: String,
    var serviceProviderId: String,
    var letterTypeId: String,
    var attachments: List<AttachmentEntity>
): Parcelable {
    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MedicalComplaintRequest> {
        override fun createFromParcel(parcel: Parcel): MedicalComplaintRequest {
            return MedicalComplaintRequest(parcel)
        }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(mobile)
        parcel.writeString(userNumber)
        parcel.writeString(benId)
        parcel.writeString(description)
        parcel.writeString(branchProfileId)
        parcel.writeString(serviceProviderId)
        parcel.writeString(letterTypeId)
    }
}