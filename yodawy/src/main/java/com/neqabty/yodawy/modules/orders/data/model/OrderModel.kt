package com.neqabty.yodawy.modules.orders.data.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OrderModel(
    @SerializedName("Address")
    val address: Address,
    @SerializedName("CancellationReasons")
    val cancellationReasons: List<String>,
    @SerializedName("CreationDate")
    val creationDate: String,
    @SerializedName("CurrentStatus")
    val currentStatus: String,
    @SerializedName("DeliveryFees")
    val deliveryFees: Int,
    @SerializedName("Id")
    val id: String,
    @SerializedName("Items")
    val items: List<Item>,
    @SerializedName("Notes")
    val notes: String,
    @SerializedName("OrderNumber")
    val orderNumber: String,
    @SerializedName("OrderPrice")
    val orderPrice: Double,
    @SerializedName("PriceBeforeDiscount")
    val priceBeforeDiscount: Double,
    @SerializedName("Pharmacy")
    val pharmacy: String?,
    @SerializedName("PrescriptionImages")
    val prescriptionImages: List<String>?
): Parcelable