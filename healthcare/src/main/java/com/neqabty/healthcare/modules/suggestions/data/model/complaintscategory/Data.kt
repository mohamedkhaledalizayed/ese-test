package com.neqabty.healthcare.modules.suggestions.data.model.complaintscategory


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("categories")
    val categories: List<Category>,
    @SerializedName("complaintTypes")
    val complaintTypes: List<Any>,
    @SerializedName("packages")
    val packages: List<Package>
)