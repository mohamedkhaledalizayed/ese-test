package com.neqabty.healthcare.modules.subscribtions.data.model.subscription


import com.google.gson.annotations.SerializedName

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