package com.neqabty.healthcare.sustainablehealth.mypackages.data.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class DeleteFollowerBody(
    @SerializedName("follower_id")
    val followerId: Int,
    @SerializedName("mobile")
    val mobile: String,
    @SerializedName("subscriber_id")
    val subscriberId: String
)