package com.neqabty.yodawy.modules.orders.data.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PagingInfoModel(
    @SerializedName("PageNumber")
    val pageNumber: Int,
    @SerializedName("PageSize")
    val pageSize: Int,
    @SerializedName("TotalCount")
    val totalCount: Int,
    @SerializedName("TotalNumberOfPages")
    val totalNumberOfPages: Int
): Parcelable