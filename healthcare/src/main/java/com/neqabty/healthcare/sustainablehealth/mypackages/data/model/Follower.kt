package com.neqabty.healthcare.sustainablehealth.mypackages.data.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

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