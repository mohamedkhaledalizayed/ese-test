package com.neqabty.domain.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AttachmentEntity(
    var fileName: String = "",
    var fileBase64: String = ""
): Parcelable