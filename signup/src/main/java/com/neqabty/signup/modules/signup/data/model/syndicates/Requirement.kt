package com.neqabty.signup.modules.signup.data.model.syndicates


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class Requirement(
    @SerializedName("entity")
    val entity: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("label")
    val label: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("optional")
    val optional: Boolean,
    @SerializedName("type")
    val type: String,
    @SerializedName("validators")
    val validators: Any
)