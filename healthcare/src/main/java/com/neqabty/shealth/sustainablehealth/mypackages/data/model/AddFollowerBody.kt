package com.neqabty.shealth.sustainablehealth.mypackages.data.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class AddFollowerBody(
    @SerializedName("followers")
    val followers: List<Follower>,
    @SerializedName("package_id")
    val packageId: String,
    @SerializedName("subscriber_id")
    val subscriberId: String
)