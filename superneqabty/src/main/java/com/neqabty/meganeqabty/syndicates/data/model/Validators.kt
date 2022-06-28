package com.neqabty.meganeqabty.syndicates.data.model


import com.google.gson.annotations.SerializedName

data class Validators(
    @SerializedName("email")
    val email: Boolean = false,
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("max")
    val max: Any? = Any(),
    @SerializedName("min")
    val min: Any? = Any(),
    @SerializedName("minLength")
    val minLength: Int = 0,
    @SerializedName("pattern")
    val pattern: String = "",
    @SerializedName("required")
    val required: Boolean = false
)