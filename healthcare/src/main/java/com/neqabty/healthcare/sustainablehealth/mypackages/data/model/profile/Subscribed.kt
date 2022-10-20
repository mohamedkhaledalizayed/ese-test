package com.neqabty.healthcare.sustainablehealth.mypackages.data.model.profile


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class Subscribed(
    @SerializedName("package")
    val packages: Package
)