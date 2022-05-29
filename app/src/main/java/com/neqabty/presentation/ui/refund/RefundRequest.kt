package com.neqabty.presentation.ui.refund

import android.os.Parcelable
import com.neqabty.domain.entities.AttachmentEntity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RefundRequest(
    var name: String,
    var mobile: String,
    var userNumber: String,
    var benId: String,
    var description: String,
    var branchProfileId: String,
    var serviceProviderId: String,
    var letterTypeId: String,
    var mobileToken: String,
    var attachments: List<AttachmentEntity>
): Parcelable