package com.neqabty.healthcare.chefaa.products.data.models

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class SearchProductBody(@SerializedName("search") val productName:String)
