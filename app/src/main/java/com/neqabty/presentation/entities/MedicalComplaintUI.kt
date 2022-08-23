package com.neqabty.presentation.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MedicalComplaintUI(
        var resultType: String = "",
        var requestID: String = "",
        var msg: String = "",
        var isSuccess: Boolean = false
): Parcelable