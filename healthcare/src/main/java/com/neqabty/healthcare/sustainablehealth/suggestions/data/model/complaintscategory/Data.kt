package com.neqabty.healthcare.sustainablehealth.suggestions.data.model.complaintscategory


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
@Keep
data class Data(
    @SerializedName("categories")
    val categories: List<Category>,
    @SerializedName("complaintTypes")
    val complaintTypes: List<Any>,
    @SerializedName("packages")
    val packages: List<Package>
)