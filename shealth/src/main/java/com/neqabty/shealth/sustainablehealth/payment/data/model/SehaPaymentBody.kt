package com.neqabty.shealth.sustainablehealth.payment.data.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class SehaPaymentBody(
    @SerializedName("payment")
    val payment: Payment
)