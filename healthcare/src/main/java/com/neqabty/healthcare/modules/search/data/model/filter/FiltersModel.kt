package com.neqabty.healthcare.modules.search.data.model.filter


import com.google.gson.annotations.SerializedName

data class FiltersModel(
    @SerializedName("governorates")
    val governorates: List<Governorate>,
    @SerializedName("professions")
    val professions: List<Profession>,
    @SerializedName("providerTypes")
    val providerTypes: List<ProviderType>,
    @SerializedName("degrees")
    val degrees: List<Degree>
)