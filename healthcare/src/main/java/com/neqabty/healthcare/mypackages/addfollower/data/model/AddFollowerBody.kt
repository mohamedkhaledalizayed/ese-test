package com.neqabty.healthcare.mypackages.addfollower.data.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

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