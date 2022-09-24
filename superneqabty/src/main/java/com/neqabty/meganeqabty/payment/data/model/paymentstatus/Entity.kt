package com.neqabty.meganeqabty.payment.data.model.paymentstatus


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class Entity(
    @SerializedName("code")
    val code: String,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)