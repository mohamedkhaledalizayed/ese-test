package com.neqabty.shealth.sustainablehealth.receipt.data.model.paymentstatus


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class Links(
    @SerializedName("entity")
    val entity: String
)