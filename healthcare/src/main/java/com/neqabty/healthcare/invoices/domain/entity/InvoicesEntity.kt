package com.neqabty.healthcare.invoices.domain.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class InvoicesEntity(
    val fullName: String,
    val mobile: String,
    val createdAt: String,
    val entity: String,
    val gatewayReferenceId: String,
    val membershipId: String,
    val netAmount: String,
    val status: String,
    val totalAmount: String,
    val totalFees: String,
    val serviceName: String
): Parcelable