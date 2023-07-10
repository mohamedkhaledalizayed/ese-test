package com.neqabty.healthcare.commen.mypackages.addfollower.data.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class AddFollowerBody(
    @SerializedName("followers")
    val followers: List<Follower>,
    @SerializedName("package_id")
    val packageId: String,
    @SerializedName("mobile")
    val mobile: String,
    @SerializedName("subscriber_id")
    val subscriberId: String
)