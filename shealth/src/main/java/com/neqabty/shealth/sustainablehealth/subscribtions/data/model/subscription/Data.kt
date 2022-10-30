package com.neqabty.shealth.sustainablehealth.subscribtions.data.model.subscription


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class Data(
    @SerializedName("client_id")
    val clientId: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("mobile")
    val mobile: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("ref_num")
    val refNum: String
)