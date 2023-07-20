package com.neqabty.healthcare.mypackages.subscriptiondetails.data.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class DeleteFollowerBody(
    @SerializedName("follower_id")
    val followerId: Int,
    @SerializedName("mobile")
    val mobile: String,
    @SerializedName("subscriber_id")
    val subscriberId: String
)