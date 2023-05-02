package com.neqabty.healthcare.chefaa.products.data.models

import com.google.gson.annotations.SerializedName

data class SearchProductBody(@SerializedName("search") val productName:String)
