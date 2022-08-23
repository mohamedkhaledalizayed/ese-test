package com.neqabty.presentation.ui.medicalComplaintRequest

import android.os.Parcelable
import com.neqabty.domain.entities.AttachmentEntity
import kotlinx.android.parcel.Parcelize

@Parcelize
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
) : Parcelable