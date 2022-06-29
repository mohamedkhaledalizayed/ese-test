package com.neqabty.meganeqabty.payment.domain.entity.paymentstatus


import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PaymentStatusEntity(
    val entity: String,
    val entityPaymentStatus: Boolean,
    val gatewayReferenceId: String,
    val id: String,
    val itemId: Int,
    val mobile: String?,
    val netAmount: String,
    val serviceAction: String,
    val totalAmount: String,
    val totalFees: String,
    val member_name: String?,
    val createdAt: String
): Parcelable