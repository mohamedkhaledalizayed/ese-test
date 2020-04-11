package com.neqabty.data.api.requests

import com.google.gson.annotations.SerializedName

data class DecryptionRequest(
        @SerializedName("SenderRequestNumber")
        var requestNumber: String = "",
        @SerializedName("RequestDecryptionKey")
        var decryptionKey: String = ""
)