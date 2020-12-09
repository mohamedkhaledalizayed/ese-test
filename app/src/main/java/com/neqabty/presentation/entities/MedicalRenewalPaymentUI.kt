package com.neqabty.presentation.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MedicalRenewalPaymentUI(
        var requestID: String = "",
        var paymentItem: PaymentItem? = null
): Parcelable {
    @Parcelize
    data class PaymentItem(
            var paymentRequestNumber: String = "",
            var amount: Int?,
            var name: String?,
            var engName: String?,
            var engNumber: String?,
            var paymentDetailsItems: List<PaymentDetailsItem>? = null
    ): Parcelable

    @Parcelize
    data class PaymentDetailsItem(
            var name: String = "",
            var totalAmount: String = ""
    ): Parcelable
}