package com.neqabty.healthcare.onboarding.contact.data.model


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class SubmitClientResponse(
    @SerializedName("message")
    val message: OuterMessage
)

data class OuterMessage(
    @SerializedName("message")
    val message: Message
)

data class Message(
    @SerializedName("message")
    val message: InnerMessage
)

data class InnerMessage(
    @SerializedName("ar")
    val ar: String
)