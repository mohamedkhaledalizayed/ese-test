package com.neqabty.healthcare.pharmacymart.orders.ui.orderdetails

interface ICancellation {
    fun cancelOrder(status: Boolean, reason: String)
}