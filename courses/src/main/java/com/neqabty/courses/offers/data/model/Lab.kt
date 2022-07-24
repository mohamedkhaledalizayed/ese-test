package com.neqabty.courses.offers.data.model


import com.google.gson.annotations.SerializedName

data class Lab(
    @SerializedName("entity")
    val entity: Entity = Entity(),
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("image")
    val image: String = "",
    @SerializedName("name")
    val name: String = ""
)