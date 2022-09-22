package com.neqabty.healthcare.modules.profile.data.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class DeleteFollowerBody(
    @SerializedName("follower_id")
    val followerId: Int,
    @SerializedName("subscriber_id")
    val subscriberId: String
)