package com.neqabty.healthcare.payment.data.model.paymentstatus


import androidx.annotation.Keep

@Keep
data class Account(
    val fullname: String?,
    val mobile: String?
)