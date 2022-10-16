package com.neqabty.presentation.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SyndicateServicesPaymentRequestUI(
    var netAmount: Double? = 0.0,
    var amount: Double? = 0.0,
    var refId: String
): Parcelable