package com.neqabty.presentation.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MedicalDirectoryProviderUI(
    var id: String = "",
    var name: String = "",
    var address: String = "",
    var phone: String = "",
    var email: String = "",
    var typeId: String = "",
    var typeName: String = ""
): Parcelable