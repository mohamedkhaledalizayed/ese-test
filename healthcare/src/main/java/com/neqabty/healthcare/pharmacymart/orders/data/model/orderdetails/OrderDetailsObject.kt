package com.neqabty.healthcare.pharmacymart.orders.data.model.orderdetails


import androidx.annotation.Keep

@Keep
data class OrderDetailsObject(val `data`: List<OrderItemModel>)