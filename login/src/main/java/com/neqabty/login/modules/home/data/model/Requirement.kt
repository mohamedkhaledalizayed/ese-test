package com.neqabty.login.modules.home.data.model


import com.google.gson.annotations.SerializedName

data class Requirement(
    @SerializedName("entity")
    val entity: Int = 0,
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("label")
    val label: Any? = Any(),
    @SerializedName("name")
    val name: String = "",
    @SerializedName("optional")
    val optional: Boolean = false,
    @SerializedName("type")
    val type: String = "",
    @SerializedName("validators")
    val validators: Validators? = null
)