package com.neqabty.data.api

import com.google.gson.annotations.SerializedName
import com.neqabty.presentation.util.Config

abstract class Request {
    @SerializedName("api_token")
    var api_token: String = Config.API_KEY
}