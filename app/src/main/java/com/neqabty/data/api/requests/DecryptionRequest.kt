package com.neqabty.data.api.requests

import com.google.gson.annotations.SerializedName
import com.neqabty.data.api.Request

data class DecryptionRequest(
    @SerializedName("SenderRequestNumber")
    var requestNumber: String = "",
    @SerializedName("RequestDecryptionKey")
    var decryptionKey: String = ""
) : Request()