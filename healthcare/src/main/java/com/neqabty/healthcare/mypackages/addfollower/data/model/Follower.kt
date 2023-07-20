package com.neqabty.healthcare.mypackages.addfollower.data.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Follower(
    @SerializedName("image")
    val image: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("national_id")
    val nationalId: String,
    @SerializedName("relation_type")
    val relationType: Int
)