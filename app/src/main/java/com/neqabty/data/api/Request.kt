package com.neqabty.data.api

import com.google.gson.annotations.SerializedName
import com.neqabty.BuildConfig

abstract class Request {
    @SerializedName("platform")
    var platform: String = "android_" + if(BuildConfig.FLAVOR_services.contains("hms")) "huawei" else "google"
}