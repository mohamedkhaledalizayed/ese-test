package com.neqabty.yodawy.modules.orders.data.model.response.order


import com.google.gson.annotations.SerializedName

data class OrderResponse(
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
    @SerializedName("OrderNumber")
    val orderNumber: String,
    @SerializedName("OrderPrice")
    val orderPrice: Int,
    @SerializedName("Pharmacy")
    val pharmacy: String,
    @SerializedName("PrescriptionImages")
    val prescriptionImages: List<Any>
)