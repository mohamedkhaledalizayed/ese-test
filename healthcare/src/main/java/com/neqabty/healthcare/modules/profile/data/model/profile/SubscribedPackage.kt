package com.neqabty.healthcare.modules.profile.data.model.profile


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class SubscribedPackage(
    @SerializedName("package")
    val packages: Package
)