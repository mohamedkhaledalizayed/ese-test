package com.neqabty.yodawy.modules.products.data.model.search


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class SearchResponse(
    @SerializedName("data")
    val data: List<Data>,
    @SerializedName("message_ar")
    val messageAr: String,
    @SerializedName("message_en")
    val messageEn: String,
    @SerializedName("status")
    val status: Boolean,
    @SerializedName("status_code")
    val statusCode: Int,
    @SerializedName("status_message_ar")
    val statusMessageAr: String,
    @SerializedName("status_message_en")
    val statusMessageEn: String
)