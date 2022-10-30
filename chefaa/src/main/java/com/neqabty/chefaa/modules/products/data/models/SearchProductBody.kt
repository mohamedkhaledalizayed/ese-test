package com.neqabty.chefaa.modules.products.data.models

import com.google.gson.annotations.SerializedName

data class SearchProductBody(@SerializedName("search") val productName:String)
