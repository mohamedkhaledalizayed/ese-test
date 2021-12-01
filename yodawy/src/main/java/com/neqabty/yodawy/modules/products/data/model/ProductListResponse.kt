package com.neqabty.yodawy.modules.products.data.model

import com.google.gson.annotations.SerializedName

data class ProductListResponse(
    @SerializedName("Data") val data: List<ProductModel>,
    @SerializedName("PagingInfo") val pagingInfoModel: PagingInfoModel
)
