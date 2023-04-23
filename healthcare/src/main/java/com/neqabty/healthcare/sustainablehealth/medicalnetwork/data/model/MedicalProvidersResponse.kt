package com.neqabty.healthcare.sustainablehealth.medicalnetwork.data.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class MedicalProvidersResponse(
    @SerializedName("current_page")
    val currentPage: Int = 0,
    @SerializedName("data")
    val data: List<MedicalProviderModel> = listOf(),
    @SerializedName("first_page_url")
    val firstPageUrl:String = "",
    @SerializedName("from")
    val from:Int = 0,
    @SerializedName("next_page_url")
    val nextPageUrl:String = "",
    @SerializedName("path")
    val path:String = "",
    @SerializedName("per_page")
    val perPage:Int = 0,
    @SerializedName("prev_page_url")
    val prevPageUrl:String = "",
    @SerializedName("to")
    val to:Int = 0,
)
